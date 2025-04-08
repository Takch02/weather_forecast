# 날씨 예보 프로그램

![Image](https://github.com/user-attachments/assets/5c3eb50a-4070-4f09-9c7e-4bfcc0205d6a)

Spring MVC를 공부하며 웹 어플리케이션을 하나 만들어 보고싶었습니다. 어떤게 좋을까 하다 API키로 정보를 가져오고 java 객체로 변환해보고 싶어 
쉬워보이는(전혀 안쉬움) 기상청 단기예보 API를 가져와 만들었습니다.

위 프로그램을 만들며 MVC 구조, thymeleaf 이용법, API 가져오는 방법에 대해 알게됐습니다.

## 데이터 변환 작업

데이터는 기상청에서 json으로 가져옵니다. WebClient 객체를 이용하여 값을 가져온 후 category, value, time, date 값을 dto 객체에 넣습니다. 완성된 객체는 List에 차곡차곡 저장합니다.

*json -> dto -> List*

그리고 제가 이용한 값은 현재 온도(temperature), 하늘 상태(SKY), 기상 상태(PTY), 시간(time), 날짜(date)만 이용합니다.
해당 값을 List에서 조회한 후 있다면 Map에 차곡차곡 저장합니다. 그리고 thymeleaf로 넘겨 출력시키는 간단한(?) 프로그램입니다.
