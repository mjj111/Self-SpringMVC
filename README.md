# Make-SpringMVC

**Make-SpringMVC**는 Spring 프레임워크에 대한 깊은 이해를 목적으로, 의존성을 제거하고 순수 Java로 Spring MVC와 IoC 컨테이너를 직접 구현한 학습용 프로젝트입니다. 이를 통해 Spring의 내부 동작 원리를 체험하며 학습할 수 있습니다.

---

## 프로젝트 개요
이 프로젝트는 Spring 프레임워크의 의존성 없이 Java를 통해, Spring MVC 구조와 IoC 컨테이너를 직접 구현하는 것을 목표로 합니다.
이를 통해 Spring의 내부 동작 원리를 깊이 이해하고, 직접 만들어보며 학습할 수 있도록 설계되었습니다.

---

## 주요 기능

### 1. MVC 구조 구현
- Model-View-Controller 패턴을 직접 구현하여 웹 애플리케이션의 기본 구조를 학습합니다.
- 요청(Request)을 Controller가 처리하고, View로 데이터를 전달하는 프로세스를 구현합니다.

### 2. IoC 컨테이너
- Inversion of Control (IoC) 컨테이너를 구현하여 객체의 의존성을 주입하고 객체 생명 주기를 관리합니다.
- 의존성 주입(Dependency Injection, DI)을 지원해 객체 간 결합도를 낮추고 유지보수성을 높입니다.

### 3. Dispatcher Servlet
- 요청을 처리하고 적절한 컨트롤러(Handler)에 전달하는 Dispatcher Servlet을 구현합니다.
- SpringMVC에서 핵심 역할을 하는 서블릿의 동작을 그대로 재현합니다.

### 4. 핸들러 매핑 및 어댑터
- 클라이언트 요청을 적절한 핸들러에 매핑해주는 핸들러 매핑과, 이를 실행하는 핸들러 어댑터를 구현합니다.
  
### 5. 뷰 리졸버
- View Resolver를 구현하여 클라이언트 요청 결과를 뷰(View)로 렌더링합니다.

---

## 프로젝트 구성도

### 1. ApplicationContext 초기화

![ApplicationContext 초기화](https://github.com/mjj111/Self-SpringMVC/assets/86913355/cac1d989-215a-46a1-92c4-4c0eb1053d3a)

#### 구성 단계:
1. 빈 설정 정보 Reader: 설정 정보를 읽고 이를 레지스트리에 저장.
2. 빈 팩토리: 레지스트리에 저장된 설정 정보를 바탕으로 빈 객체 생성 요청 처리.
3. 빈 유틸: 의존성을 주입해 완전한 빈 객체를 생성하여 반환.

#### 주요 특징:
- Annotation 기반으로 DI(Dependency Injection)를 처리.
- 빈 생성을 단일 책임으로 분리하여 가독성과 유지보수성을 확보.
  
---

### 2. Dispatcher Servlet 초기화

![Dispatcher Servlet 초기화](https://github.com/mjj111/Self-SpringMVC/assets/86913355/57f506dc-0c75-4e3b-9333-9f6ac95ee9c6)

#### 구성 단계:
1. 서블릿 컨테이너 초기화: 서블릿 컨테이너가 웹 애플리케이션을 초기화하고, Dispatcher Servlet을 생성.
2. 애플리케이션 컨텍스트: 설정 정보를 기반으로 빈 팩토리와 Annotation 설정 메타정보를 초기화.
3. 핸들러 매핑: 요청 처리에 적합한 핸들러를 매핑.

#### 주요 특징:
- 모든 요청은 **Dispatcher Servlet**을 거쳐 처리.
- MVC 구조와 IoC의 핵심 역할 통합.

---

### 3. SpringMVC 동작 흐름

![SpringMVC 동작 흐름](https://github.com/mjj111/Self-SpringMVC/assets/86913355/b786f7a7-22ba-49a9-b05f-e78555c2f936)

#### 동작 순서:
1. 클라이언트 요청: 클라이언트가 HTTP 요청을 보냅니다.
2. Dispatcher Servlet: 요청을 받아 적절한 핸들러를 조회합니다.
3. 핸들러 매핑: 요청에 맞는 핸들러를 찾기 위해 핸들러 어댑터를 조회합니다.
4. 핸들러 어댑터: 적절한 핸들러를 실행합니다.
5. 핸들러: 요청을 처리하고 ModelAndView 객체를 반환합니다.
6. Dispatcher Servlet: 뷰 리졸버를 호출하여 적절한 뷰를 반환합니다.
7. 뷰 리졸버: 요청 결과를 렌더링하고 클라이언트에 응답을 전달합니다.
