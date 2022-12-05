# 싱글톤 컨테이너

- ## 웹 애플리케이션과 싱글톤
    - 보통 여러 고객이 동시에 요청을 한다.
    - 스프링이 없는 순수한 DI 컨테이너 테스트
        - 순수한 DI 컨테이너인 AppConfig는 요청이 올 때마다 객체를 새로 생성한다. → 메모리 낭비가 심하다. → 객체가 1개만 생성되고 공유하도록 설계하면 된다. → 싱글톤 패턴 이라고 한다.
- ## 싱글톤 패턴
    - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
    - static 영역에 객체 인스턴스를 미리 생성해놓는다. 이 인스턴스가 필요하면 오직 getter를 통해서만 조회할 수 있다. 항상 같은 인스턴스를 반환한다. 딱 1개의 객체 인스턴스만 존재해야 하므로 , 생성자를 private로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
    - `isEqualTo` 동등성 비교 , `sameAs` 동일성 비교
    - 스프링 컨테이너에서는 기본적으로 싱글톤 패턴을 적용해주기 때문에 별도로 구현해야 할 필요는 없다.
    - **문제점**
        - 싱글톤 패턴을 구현하는 코드 자체가 많다.
        - 의존관계상 클라이언트가 구체 클래스에 의존한다. → DIP 를 위반한다.
        - 구체 클래스에 의존하면 OCP 원칙을 위반할 가능성이 높아진다.
        - 테스트 , 내부속성 변경 초기화 , private 생성자로 자식 클래스 생성 이 모두 어렵다
        - 유연성이 떨어지기 때문에 안티패턴으로 불리기도 한다. → 스프링의 싱글톤 패턴 구현 방식은 조금 다르다. 유연성이나 자바 코드만으로 이를 구현했을 때 나오는 단점들을 상쇄시킬 수 있다.
- ## 싱글톤 컨테이너
    - 스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하고, 객체 인스턴스를 싱글톤으로 관리한다. 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리한다. 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이런 기능을 싱글톤 레지스트리라고 한다.
    - 스프링의 기본 빈 등록 방식은 싱글톤이지만 , 그 외에도 다른 방식도 지원하고 있다. 이 기능에 대해서는 빈 스코프에서..
- ## 싱글톤 방식의 주의점
    - 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful) 하게 설계하면 안된다. → 무상태(stateless)로 설계해야 한다.
    - 특정 클라이언트에 의존적인 필드가 있어서는 안된다.
    - 특정 클라이언트가 값을 변경할 수 있으면 안된다. → 가급적 읽기만 가능해야 함.
    - 공유 필드가 변경되는 경우가 발생하면 문제가 발생한다. 따라서 공유 필드가 아니라 지역변수, 파라미터 , ThreadLocal 등을 사용해야 한다.
- ## `@Configuration` 과 싱글톤
    - AppConfig 자바 코드에서 `new MemoryMemberRepository` 를 여러 번 호출하는 것으로 보인다.
    - 그러나 확인해보면 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다. 스프링 컨테이너가 각각 `Bean` 을 호출해서 스프링 빈을 생성하는 데 여기서 싱글톤을 유지하기 위해서 스프링이 작동한다.
- ## `@Configuration`과 바이트코드 조작
    - 스프링이 자바 코드 자체를 조작하기는 어렵기 때문에 클래스의 바이트 코드를 조작하는 라이브러리를 사용한다. 스프링에서는 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들어 낸다. 바로 이 클래스가 싱글톤이 보장되도록 해준다.
    - 내부 구조는 아마도 스프링 컨테이너에서 존재하는 빈을 찾아서 반환하고 스프링 빈이 없다면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 있을 것이다. ( GCLIB )
    - 만약 `@Configuration` 가 없이 `@Bean` 만 있다면 CGLIB 기술이 없기 때문에 싱글톤을 보장할 수 없다. 큰 고민 없이 스프링 설정 정보는 항상 `@Configuration` 을 사용하자.