# Movie Recommendation System: Movieblock
for UNIST CSE364 Software Engineering

## Milestone 3

### What did we do
#### Repository structure
```
.
├── data
│   ├── links.dat
│   ├── movies.dat
│   ├── ratings.dat
│   └── users.dat
├── Dockerfile
├── pom.xml
├── README.md
├── run.sh
└── src
    ├── main
    │   └── java
    │       └── se
    │           └── group2
    │               ├── ApiError.java
    │               ├── Application.java
    │               ├── InvalidInputException.java
    │               ├── MovieblockController.java
    │               ├── Movieblock.java
    │               ├── MovieRecommendationInput.java
    │               ├── RecommendationEngine.java
    │               ├── RecommendationOutput.java
    │               └── UserRecommendationInput.java
    └── test
        └── java
            └── se
                └── group2
                    ├── ApiErrorTest.java
                    ├── ApplicationTest.java
                    ├── InvalidInputExceptionTest.java
                    ├── MovieblockControllerTest.java
                    ├── MovieblockTest.java
                    ├── MovieRecommendationInputTest.java
                    ├── RecommendationEngineTest.java
                    ├── RecommendationOutputTest.java
                    └── UserRecommendationInputTest.java

10 directories, 26 files
```
We implemented REST APIs for recommending movies using Spring Framework.

### How our recommendation algorithm works

#### About implementation
The recommendation algorithm is almost the same as in Milestone 2. ([Algorithm Description](xxx://github.com/rocky112358/UNIST_CSE364_Group2/blob/feature/73-read-me/README.md#L232
)) 

One difference is that when a movie title is entered as the input, it looks for the genre of the movie in `movies.dat` and recommends popular movies of the genre regardless of gender, age, or occupation.

If a limit is given, only the number of movies are printed, sorted from most popular. If not, top 10 movies are printed by default.

### How to install the program
1. Download Dockerfile and run.sh from Blackboard.

2. Put dockerfile and run.sh in the same directory path, build the image, and run the container.
```text
$ docker build -t milestone3 .

Sending build context to Docker daemon  31.44MB
Step 1/9 : FROM ubuntu:20.04
 ---> 4dd97cefde62
Step 2/9 : ARG DEBIAN_FRONTEND="noninteractive"
 ---> Running in 008b87e642bb
Removing intermediate container 008b87e642bb
 ---> 9b39fb815563
Step 3/9 : RUN apt-get update
 ---> Running in 66b60d257f60
Get:1 http://security.ubuntu.com/ubuntu focal-security InRelease [114 kB]
Get:2 http://archive.ubuntu.com/ubuntu focal InRelease [265 kB]
Get:3 http://security.ubuntu.com/ubuntu focal-security/main amd64 Packages [829 kB]
Get:4 http://archive.ubuntu.com/ubuntu focal-updates InRelease [114 kB]
Get:5 http://security.ubuntu.com/ubuntu focal-security/universe amd64 Packages [716 kB]
Get:6 http://security.ubuntu.com/ubuntu focal-security/restricted amd64 Packages [274 kB]
Get:7 http://security.ubuntu.com/ubuntu focal-security/multiverse amd64 Packages [27.6 kB]
Get:8 http://archive.ubuntu.com/ubuntu focal-backports InRelease [101 kB]
Get:9 http://archive.ubuntu.com/ubuntu focal/main amd64 Packages [1275 kB]
Get:10 http://archive.ubuntu.com/ubuntu focal/multiverse amd64 Packages [177 kB]
Get:11 http://archive.ubuntu.com/ubuntu focal/universe amd64 Packages [11.3 MB]
Get:12 http://archive.ubuntu.com/ubuntu focal/restricted amd64 Packages [33.4 kB]
Get:13 http://archive.ubuntu.com/ubuntu focal-updates/restricted amd64 Packages [299 kB]
Get:14 http://archive.ubuntu.com/ubuntu focal-updates/main amd64 Packages [1246 kB]
Get:15 http://archive.ubuntu.com/ubuntu focal-updates/multiverse amd64 Packages [29.8 kB]
Get:16 http://archive.ubuntu.com/ubuntu focal-updates/universe amd64 Packages [973 kB]
Get:17 http://archive.ubuntu.com/ubuntu focal-backports/universe amd64 Packages [4305 B]
Fetched 17.8 MB in 7s (2722 kB/s)
Reading package lists...
Removing intermediate container 66b60d257f60
 ---> 6e63a3a16227
Step 4/9 : RUN apt-get -y install vim git openjdk-11-jdk maven

...

done.
done.
Removing intermediate container 7fda52e27c8a
 ---> 7419a62837c9
Step 5/9 : RUN mkdir -p /root/project
 ---> Running in c7e1f4572294
Removing intermediate container c7e1f4572294
 ---> 1cb22177da3c
Step 6/9 : WORKDIR /root/project
 ---> Running in 5083ce0a8d8c
Removing intermediate container 5083ce0a8d8c
 ---> fa78bbfdeffd
Step 7/9 : COPY run.sh /root/project
 ---> 5b7a4d166652
Step 8/9 : RUN chmod 755 run.sh
 ---> Running in 7e4030ce7ab5
Removing intermediate container 7e4030ce7ab5
 ---> e750116ef5ec
Step 9/9 : RUN /bin/bash
 ---> Running in dd6a09bd56f3
Removing intermediate container dd6a09bd56f3
 ---> 8c2337ae710e
Successfully built 8c2337ae710e
Successfully tagged milestone3:latest

$ docker run -it milestone3
root@6738818f1e19:~/project#

```
3. Execute run.sh in container. (About 10 minutes)
```text
root@[containerID]:~/project# . run.sh 
Cloning into 'UNIST_CSE364_Group2'...
Username for 'https://github.com': [your username]
Password for 'https://[your username]@github.com': [your password]

...

[INFO] Building jar: /root/project/UNIST_CSE364_Group2/target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  06:38 min
[INFO] Finished at: 2021-05-24T14:25:00Z
[INFO] ------------------------------------------------------------------------


  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v2.3.10.RELEASE)

2021-05-24 14:25:01.675  INFO 522 --- [           main] se.group2.Application                    : Starting Application on 6738818f1e19 with PID 522 (/root/project/UNIST_CSE364_Group2/target/cse364-project-1.0-SNAPSHOT.jar started by root in /root/project/UNIST_CSE364_Group2)
2021-05-24 14:25:01.678  INFO 522 --- [           main] se.group2.Application                    : No active profile set, falling back to default profiles: default
2021-05-24 14:25:02.629  INFO 522 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2021-05-24 14:25:02.641  INFO 522 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-05-24 14:25:02.642  INFO 522 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.45]
2021-05-24 14:25:02.705  INFO 522 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2021-05-24 14:25:02.705  INFO 522 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 972 ms
2021-05-24 14:25:02.887  INFO 522 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-05-24 14:25:03.032  INFO 522 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-05-24 14:25:03.044  INFO 522 --- [           main] se.group2.Application                    : Started Application in 1.752 seconds (JVM running for 2.123)
...
```

### How to run the program
1. After installing the program, you need to access another terminal session in the same server.

2. HostOS에서 Spring web server를 띄우고 있는 docker 컨테이너가 정상적으로 동작하는지 확인한다.
3. 해당 명령어를 통해 docker 컨테이너로 접근한다
```
docker ps - a | grep image_name
docker exec -it container_name bash
```
![image](https://user-images.githubusercontent.com/17242507/119365548-fbee4e00-bcea-11eb-910e-9c58658f297b.png)

4. docker 컨테이너 안에서 curl 명령어를 통해 기능을 동작시킬 수 있다.
> 기능에 따라 접근하는 URL 주소가 다르다는것을 명심할 것
```
(1) part I: Recommend Top 10 movies given user data (gender, age, occupation, genres)

In part I, given a user data, It provides a REST API that shows a list of top 10 recommended movies for a given user.

curl -X GET http://localhost:8080/users/recommendations -H 'Content-type:application/json' -d '{"gender": user_gender, "age": user_age, "occupation": user_occupation, "genres": user_genres}'

-d 옵션에는 "gender", "age", "occupation", "genres"가 모두 기재되어있어야 함
user data의 경우에는 case insensitive
모든 경우를 추가하고 싶으면 ""
genres의 경우에는 다중 검색을 지원
없는 경우에는 error 반환
[출력결과물]

```
```
(2) part II: Recommend movies given a movie title

In Part II, given a user’s favorite movie title, It provide a REST API that shows a list of recommended movies that contains the given number of movies

curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title": "Toy Story (1995)"}'
curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title": "Toy Story (1995)", "limits" : positive_integer}'
-d 옵션에는 title의 경우에는 항상 기재되어 있어야 하지만 limits는 선택사항임
  title이 IMDb data 내에 존재하지 않을 경우 error를 반환함
  영화 이름은 case-sensitive함
극단적으로 큰 숫자를 입력할 경우에는 해당 숫자 범위 내에서 출력가능한 모든 영화를 알고리즘대로 출력함.
IMDb 내에 영화가 3883개만 존재
리뷰가 5개 이하인 영화는 출력되지 않음

limits는 양의 정수 값을 가지나, 만약 입력되지 않을 경우에는 default옵션인 10개가 출력된다.
  음의정수, 소수 등 적합하지 않은 값이 입력 될 경우에는 error를 출력함
[출력결과물]

```

### Roles of each member
Everyone has contributed to all parts. However in this part, we will mention the part that each has mainly contributed.
* 20151054 김동민: AAA
* 20151467 장동원: BBB
* 20171108 박다현: CCC

---

## ~~Milestone 2~~ (Deprecated)

<details>
  <summary> Click to expand! </summary>

> **This program no longer behaves like **How to run the program** described below in the lastest commit. (as of May 23, 2021)**
> **This version is tagged as "milestone-2" and you may checkout if you like.**

### What did we do
#### Repository structure
```
.
├── data
│   ├── links.dat
│   ├── movies.dat
│   ├── ratings.dat
│   └── users.dat
├── Dockerfile
├── pom.xml
├── README.md
├── run.sh
└── src
    ├── main
    │   └── java
    │       └── se
    │           └── group2
    │               ├── Movieblock.java
    │               └── RecommendationEngine.java
    └── test
        └── java
            └── se
                └── group2
                    ├── MovieblockTest.java
                    └── RecommendationEngineTest.java

10 directories, 12 files

```
We implemented features that recommend Top 10 movies given user data and recommend Top 10 movies for given categories.

### How our recommendation algorithm works

#### About implementation

Our recommendation engine is implemented in RecomendationEngine.java (se.group2.RecommendationEngine)

The recommendation system takes 4 inputs, gender, age, occupation and genre(s), and the last one is optional.

When recommendMovies() method is called with the arguments above, it returns a list of Movies, a length of 10. The method loads data with default filenames if they are not loaded before being called. ([line 107-115](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L107-L115
))

Then gives score to each movie with an algorithm described as below. ([line 117-159](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L117-L159
))

Next, sort them in descending order by score. ([line 161-163](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L161-L163
))

Finally, iterating over the sorted list of movies, add the movie to 'recommendations' list, which is to be returned to the caller, until the size of it reaches 10. During the iteration, the movie rated less than 5 times or the movie which does not belong to the genre are excluded (if specified). ([line 165-185](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L165-L185
))

#### About our algorithm design

First, the engine divides the users into 8 groups and count number of users belong to each group. In the table below, Y means that the user has same property with the input user, and N means not. ([line 117-126](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L165-L185
))

```
  Same?  |  Gender | Age | Occupation
---------+---------+-----+------------
 Group 0 |      N  |   N |          N 
 Group 1 |      N  |   N |          Y 
 Group 2 |      N  |   Y |          N 
 Group 3 |      N  |   Y |          Y 
 Group 4 |      Y  |   N |          N 
 Group 5 |      Y  |   N |          Y 
 Group 6 |      Y  |   Y |          N 
 Group 7 |      Y  |   Y |          Y 
```

Before we introduce our algorithm, let's define a term 'groupRatio'. This is the ratio of each group among all users. groupRatio will have a value of 0 or more, but less than 1.

Now, iterate over ratings one by one and add score of the movie to corresponding variable. The score is slightly modified with a formula below before being added. `r` is the groupRatio of the group which the user made the rating belongs to.

![image](https://user-images.githubusercontent.com/6457345/116816565-6ead5300-ab9d-11eb-9cd9-6157eff6823f.png)

The weight will be closer to 1 if the group is larger, and close to e(=2.71828..., aka. Euler's number) if the group is smaller.

Sum up these weighted scores and divide them with the number of ratings of each movie to find the average. ([line 136-149](https://github.com/rocky112358/UNIST_CSE364_Group2/blob/9edf5288b0f565a2057b99027c13e57bb87b3330/src/main/java/se/group2/RecommendationEngine.java#L136-L149
))

What we think of in this algorithm is to emphasize evaluation of the user as there are fewer people who match the characteristics of the user given as input.

This recommendation system is in a situation where it is necessary to recommend movies only with the characteristics of the user without the user's previous viewing record or evaluation record data, which can be considered as a cold start. Therefore, we designed this algorithm because we had to find the biases of people that match the characteristics of the user given as input in the data we have, and we thought that movies that matches this should be recommended.

### How to install the program
1. Download Dockerfile and run.sh from Blackboard.

2. Put dockerfile and run.sh in the same directory path, build the image, and run the container.
```text
$ docker build -t milestone2 .

Sending build context to Docker daemon  31.15MB
Step 1/9 : FROM ubuntu:20.04
 ---> 4dd97cefde62
Step 2/9 : ARG DEBIAN_FRONTEND="noninteractive"
 ---> Running in 6ada22ed6bce
Removing intermediate container 6ada22ed6bce
 ---> d47094ab4f96
Step 3/9 : RUN apt-get update
 ---> Running in 07ba64ae9eb7
Get:1 http://security.ubuntu.com/ubuntu focal-security InRelease [109 kB]
Get:2 http://archive.ubuntu.com/ubuntu focal InRelease [265 kB]
Get:3 http://security.ubuntu.com/ubuntu focal-security/main amd64 Packages [783 kB]
Get:4 http://archive.ubuntu.com/ubuntu focal-updates InRelease [114 kB]
Get:5 http://security.ubuntu.com/ubuntu focal-security/restricted amd64 Packages [243 kB]
Get:6 http://security.ubuntu.com/ubuntu focal-security/multiverse amd64 Packages [21.7 kB]
Get:7 http://security.ubuntu.com/ubuntu focal-security/universe amd64 Packages [690 kB]
Get:8 http://archive.ubuntu.com/ubuntu focal-backports InRelease [101 kB]
Get:9 http://archive.ubuntu.com/ubuntu focal/main amd64 Packages [1275 kB]
Get:10 http://archive.ubuntu.com/ubuntu focal/universe amd64 Packages [11.3 MB]
Get:11 http://archive.ubuntu.com/ubuntu focal/restricted amd64 Packages [33.4 kB]
Get:12 http://archive.ubuntu.com/ubuntu focal/multiverse amd64 Packages [177 kB]
Get:13 http://archive.ubuntu.com/ubuntu focal-updates/main amd64 Packages [1198 kB]
Get:14 http://archive.ubuntu.com/ubuntu focal-updates/multiverse amd64 Packages [29.7 kB]
Get:15 http://archive.ubuntu.com/ubuntu focal-updates/restricted amd64 Packages [274 kB]
Get:16 http://archive.ubuntu.com/ubuntu focal-updates/universe amd64 Packages [958 kB]
Get:17 http://archive.ubuntu.com/ubuntu focal-backports/universe amd64 Packages [4305 B]
Fetched 17.6 MB in 7s (2608 kB/s)
Reading package lists...
Removing intermediate container 07ba64ae9eb7
 ---> 13694ac46d86
Step 4/9 : RUN apt-get -y install vim git openjdk-11-jdk maven

...

done.
done.
Removing intermediate container f3f6a6f02cf0
 ---> f8195c96554b
Step 5/9 : RUN mkdir -p /root/project
 ---> Running in 6781fd964858
Removing intermediate container 6781fd964858
 ---> 0fceea64ca56
Step 6/9 : WORKDIR /root/project
 ---> Running in 2608ae8e5c10
Removing intermediate container 2608ae8e5c10
 ---> 94d90805e25f
Step 7/9 : COPY run.sh /root/project
 ---> b0852ff8a386
Step 8/9 : RUN chmod 755 run.sh
 ---> Running in 7a14b0eaa489
Removing intermediate container 7a14b0eaa489
 ---> 4c8eca16cff0
Step 9/9 : RUN /bin/bash
 ---> Running in fd0f666e9ee9
Removing intermediate container fd0f666e9ee9
 ---> 8183698a46fa
Successfully built 8183698a46fa
Successfully tagged milestone2:latest

$ docker run -it milestone2
root@5eda5c29a68e:~/project#

```

3. Execute run.sh in container.
```text
root@[containerID]:~/project# . run.sh 
Cloning into 'UNIST_CSE364_Group2'...
Username for 'https://github.com': [your username]
Password for 'https://[your username]@github.com': [your password]
remote: Enumerating objects: 370, done.
remote: Counting objects: 100% (370/370), done.
remote: Compressing objects: 100% (165/165), done.
remote: Total 370 (delta 105), reused 308 (delta 67), pack-reused 0
Receiving objects: 100% (370/370), 5.80 MiB | 4.98 MiB/s, done.
Resolving deltas: 100% (105/105), done.
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------< unist:cse364-project >------------------------
[INFO] Building cse364-project 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------

...

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.group2.MovieblockTest
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.094 sec
Running se.group2.RecommendationEngineTest
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 23.693 sec

Results :

Tests run: 25, Failures: 0, Errors: 0, Skipped: 0

...

[INFO] Installing /root/project/UNIST_CSE364_Group2/target/cse364-project-1.0-SNAPSHOT.jar to /root/.m2/repository/unist/cse364-project/1.0-SNAPSHOT/cse364-project-1.0-SNAPSHOT.jar
[INFO] Installing /root/project/UNIST_CSE364_Group2/pom.xml to /root/.m2/repository/unist/cse364-project/1.0-SNAPSHOT/cse364-project-1.0-SNAPSHOT.pom
[INFO] Installing /root/project/UNIST_CSE364_Group2/target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar to /root/.m2/repository/unist/cse364-project/1.0-SNAPSHOT/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:51 min
[INFO] Finished at: 2021-05-02T11:33:11Z
[INFO] ------------------------------------------------------------------------

I Am Cuba (Soy Cuba/Ya Kuba) (1964) (http://www.imdb.com/title/tt0058604)
Lamerica (1994) (http://www.imdb.com/title/tt0110299)
Apple, The (Sib) (1998) (http://www.imdb.com/title/tt0156901)
Sanjuro (1962) (http://www.imdb.com/title/tt0056443)
Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954) (http://www.imdb.com/title/tt0047478)
Shawshank Redemption, The (1994) (http://www.imdb.com/title/tt0111161)
Godfather, The (1972) (http://www.imdb.com/title/tt0068646)
Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
Usual Suspects, The (1995) (http://www.imdb.com/title/tt0114814)
Schindler's List (1993) (http://www.imdb.com/title/tt0108052)

Sanjuro (1962) (http://www.imdb.com/title/tt0056443)
Raiders of the Lost Ark (1981) (http://www.imdb.com/title/tt0082971)
Star Wars: Episode IV - A New Hope (1977) (http://www.imdb.com/title/tt0076759)
Lawrence of Arabia (1962) (http://www.imdb.com/title/tt0056172)
Great Escape, The (1963) (http://www.imdb.com/title/tt0057115)
Dersu Uzala (1974) (http://www.imdb.com/title/tt0071411)
Princess Bride, The (1987) (http://www.imdb.com/title/tt0093779)
Star Wars: Episode V - The Empire Strikes Back (1980) (http://www.imdb.com/title/tt0080684)
Treasure of the Sierra Madre, The (1948) (http://www.imdb.com/title/tt0040897)
African Queen, The (1951) (http://www.imdb.com/title/tt0043265)
root@5eda5c29a68e:~/project/UNIST_CSE364_Group2#
```

### How to run the program
After the installation, you can run the program with other args in the container:

```text
root@5eda5c29a68e:~/project/UNIST_CSE364_Group2# pwd
/root/project/UNIST_CSE364_Group2
root@5eda5c29a68e:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "F" "18" "k-12student"
Ed's Next Move (1996) (http://www.imdb.com/title/tt0116167)
Apple, The (Sib) (1998) (http://www.imdb.com/title/tt0156901)
I Am Cuba (Soy Cuba/Ya Kuba) (1964) (http://www.imdb.com/title/tt0058604)
N?nette et Boni (1996) (http://www.imdb.com/title/tt0117221)
Eighth Day, The (Le Huiti?me jour ) (1996) (http://www.imdb.com/title/tt0116581)
Shawshank Redemption, The (1994) (http://www.imdb.com/title/tt0111161)
Close Shave, A (1995) (http://www.imdb.com/title/tt0112691)
Wrong Trousers, The (1993) (http://www.imdb.com/title/tt0108598)
Usual Suspects, The (1995) (http://www.imdb.com/title/tt0114814)
Schindler's List (1993) (http://www.imdb.com/title/tt0108052)
```

You can copy the command below and modify it:
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock GENDER AGE OCCUPATION [GENRES]
```
* All arguments must be entered enclosed in quotation marks.

### Supported inputs and expected ouputs when inputs are not supported
The program takes 3 or 4 arguments, gender, age, occupation and a list of genres (optional).
> CAUTION! If you enter the genres argument, the genres argument cannot be empty.

First, Gender can have one of three values: F, M, and "".
In the case of blank, the movie is recommended regardless of gender.

```sh
# gender argument example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "F" "" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "M" "" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" ""
``` 

Gender arguments are case-insensitive.
```sh
# case-insensitive example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "f" "" ""
```

If an incorrect value is entered for the gender, an error is displayed.
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "D" "" ""
Error: invalid gender input
```

Second, In the case of age, you can enter 0 or a decimal positive integer or "".
It is possible to enter a value that is accepted as a decimal positive number in [parseInt]( https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#parseInt(java.lang.String) ).

In the case of blank, the movie is recommended regardless of age.

```sh
# age argument example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "0" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "100" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "00253" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "+10" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" ""
```

If an incorrect value is entered for the age, an error is displayed.
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "-15" ""
Error: invalid age input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "16.1" "" #only integer
Error: invalid age input
```

Third, an occupation can be only one of the values below:
```
other
academic
educator
artist
clerical
admin
collegestudent
gradstudent
grad student
customerservice
customer service
doctor
healthcare
health care
executive
managerial
farmer
homemaker
k-12student
k-12 student
lawyer
programmer
retired
sales
marketing
scientist
self-employed
technician
engineer
tradesman
craftsman
unemployed
writer
```

```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "gradstudent"
```

Occupation arguments are case-insensitive.
```sh
# case-insensitive example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "GradsTudent"
```

If an incorrect value is entered for the occupation, an error is displayed.
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "dog"
Error: invalid occupation input
```

Fourth, a list of genres can be entered as an optional argument.
> CAUTION! If you enter the genres argument, the genres argument cannot be empty.

a list of genres can contain words below, with | as delimiter.
Note that for the category input, “|” means “or”, not “and”.
For example, for a category input “Action|Comedy”, the user likes those movies categorized to either Action or Comedy, not both Action and Comedy.

```sh
# multiple genres example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "Action|Comedy"
``` 

List of availble genre:
```
action
adventure
animation
children's
comedy
crime
documentary
drama
fantasy
film-noir
horror
musical
mystery
romance
sci-fi
thriller
war
western
```

Genres arguments are case-insensitive.
```sh
# case-insensitive example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "aniMation"
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "aniMation|CriMe"
```

If an incorrect value is entered for the genres, an error is displayed.
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "" #cannot empty
Error: invalid genre input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "nongdamgom"
Error: invalid genre input
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "" "" "" "animation|kpop" # If at least one genre is wrong
Error: invalid genre input
```

### Roles of each member
Everyone has contributed to all parts. However in this part, we will mention the part that each has mainly contributed.
* 20151054 김동민: Implement recommendation algorithm (Part 1)
* 20151467 장동원: Write a test case, program test in docker (part 3)
* 20171108 박다현: Implement recommendation by genre input (Part 2)

---

</details>

## ~~Milestone 1~~ (Deprecated)

<details>
  <summary> Click to expand! </summary>

> **This program no longer behaves like **How to run the program** described below in the lastest commit. (as of May 2, 2021)**
> **This version is tagged as "milestone-1" and you may checkout if you like.**

### What did we do
#### Repository structure
```
.
├── data
│   ├── movies.dat
│   ├── ratings.dat
│   └── users.dat
├── Dockerfile
├── pom.xml
├── README.md
├── run.sh
└── src
    └── main
        └── java
            └── se
                └── group2
                    └── Movieblock.java
```

We implemented a program that takes 2 args from user input, a list of genres and an occupation, then prints the average score of the movie of the genre, by user of the occupation.

### How to install the program
1. Download Dockerfile and run.sh from Blackboard

2. Put dockerfile and run.sh in the same directory path, build the image, and run the container.
```bash
$ ls  # Dockerfile and run.sh should be in the same directory
Dockerfile run.sh
$ docker build -t group2 .
$ docker run -it group2
```

3. Execute run.sh in container.
```sh
root@[containerID]:~/project# . run.sh 
Cloning into 'UNIST_CSE364_Group2'...
Username for 'https://github.com': [your username]
Password for 'https://[your username]@github.com': [your password]
(mvn install runs)
(java program runs with two args: Adventure educator)
3.42427802252
root@[containerID]:~/project/UNIST_CSE364_Group2#
```

### How to run the program
After the installation, you can run the program with other args in the container: 

```sh
root@[containerID]:~/project/UNIST_CSE364_Group2# pwd
/root/project/UNIST_CSE364_Group2
root@[containerID]:~/project# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock Comedy Artist
```
You can copy the command below and modify it:
```sh
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock [a list of genres] [occupation]
```

The program takes exactly 2 arguments, a list of genres and an occupation, successively.

The arguments are case-insensitive.
```sh
# case-insensitive example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock hOrROr educator
```

First, a list of genres can contain words below, with | as delimiter.
When more than two words are passed, it is treated as an input to find movies which contains all of them.

***BE CAREFUL!*** When you want to pass more then two genres, quote them to avoid misinterpretation of bash, treating | character as a pipe.
```sh
# multiple arguments example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "Adventure|Comedy" K-12student
``` 

List of availble genre:
```
action
adventure
animation
"children's"  # quote me!
comedy
crime
documentary
drama
fantasy
film-noir
horror
musical
mystery
romance
sci-fi
thriller
war
western
```

***BE CAREFUL!*** Please quote when passing "children's" as the first arg. Bash interprets this one as opening a single quote
```sh
# example
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "Children's" K-12student
``` 

Second, an occupation can be only one of the values below:
```
other
academic
educator
artist
clerical
admin
collegestudent
gradstudent
customerservice
doctor
healthcare
executive
managerial
farmer
homemaker
k-12student
lawyer
programmer
retired
sales
marketing
scientist
self-employed
technician
engineer
tradesman
craftsman
unemployed
writer
```

This program prints an error message when invalid arguments are passed, then exits with return code of `-1`
1. Number of arguments are incorrect: `"Error: 2 args are required"`
2. Invalid list of genres as passed: `"Error: invalid genre input"`
3. Invalid occupation is passed: `"Error: invalid occupation input"`
4. No movies with the list of genres: `"Error: There are no movies with that category(categories)"`

```sh
root@1684e8a105c0:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock asdf              
Error: 2 args are required
root@1684e8a105c0:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock abcdefghi educator
Error: invalid genre input
root@1684e8a105c0:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "adventure||animation" educator
Error: invalid genre input
root@1684e8a105c0:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock adventure ceo
Error: invalid occupation input
root@1684e8a105c0:~/project/UNIST_CSE364_Group2# java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar se.group2.Movieblock "adventure|comedy|action|crime|animation" educator
Error: There are no movies with that category(categories)
```

### Roles of each member
20151054 김동민: Implement the logic of the average score calculation

20151467 장동원: Set up Docker environment, test image build

20171108 박다현: Download dataset, test input validation logic

</details>
