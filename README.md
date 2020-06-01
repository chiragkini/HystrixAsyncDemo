# HystrixAsyncDemo

## Introduction

In microservice architecture we often have to get data from various sources such as third party webservices, distributed data channels or even within our application microservices which are distributed accross network.
Lets take a scenario where you need to fetch data from multiple sources and combine into single result and return to the caller.
Eg. We have GetMyDataService which calls below services:
- Service 1 ---> get data from webservices mywebservice.com (Response time 10 Secs , timeout 15 secs)
- Service 2 ---> get data from some third party api, https://reqres.in/ (Response time 5 secs, timeout 15 secs)
- Service 3 ---> get data from one of your own microservice /myMicroservice/api/getData (Response time 1 secs, timeout 15 secs)

So in synchronous mode GetMyDataService will take atleast 10 + 5 + 1 = 16 secs even if the all three services are running properly. Lets say 
service 1 & 2 is down. Now the response time for GetMyDataService will be 15 + 15 +1 =31 seconds to get response. (Response includes only result fron service 3) 

What if timeout was not configured??? The application would have hanged, waiting for response forever.

To overcome above situation there is rescuer "Hystrix" from Netflix. In this project we would demonstrate below solution:
- The GetMyDataService will call all 3 services with timeout of 15 seconds. We will receive the whatever response we get in max 15 seconds or less.
- On failure of any service or timeout we will have fallback with interrupting the thread which is waiting for service and return blank result.

##Project Setup
This is Spring boot project. Import the project in eclipse or sts.
For detailed explaination on Hystrix check : https://github.com/Netflix/Hystrix/wiki/How-To-Use

##Explaination 
This is project We have two rest controllers ExternalServiceOneController and ExternalServiceTwoController which we are considering as two external services whose data will be combined by our service named MyService.

We also have our rest endpoint 'getStatesList' which calls MyService which in turn return the combine data.

ExternalServiceOneController - returns data after 2 seconds (mocked the wait on thread)
ExternalServiceTwoController - returns data after 5 seconds (mocked the wait on thread)

Test Scenarios: 

 Hystrix Thread Timeout      | Response Time Service 1 | Response Time Service 2 |Response Time Expected | Result expected |
------------- | ----------------------- |-----------------------  |-----------------------|-----------------|
6000 ms       | 2000 ms            |5000 ms |5000 ms     |Data from service 1 & 2
4000  ms     | 2000 ms            | 5000 ms |4000 ms  | Data from service 1 only
