# Encryption util


## usage

```$ java -jar encryption-1.0.jar [KEY] [PLAN_TEXT]```

## Needs

* JDK 1.7 or higher

* Install JCE(Java Cryptography Extension)
	* [How to install JCE](https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#newwindow=1&q=how+to+install+jce)


## Description

기존 생성자를 사용하여 생성하였던 AES256 암호화 유틸을 `Effective Java 규칙1(생성자 대신 정적 팩터리 메서드를 사용할 수 없는지 생각해보라)` 에 입각하여 Builder 를 사용하여 재구성하였음