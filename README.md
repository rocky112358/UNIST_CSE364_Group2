# Movie Recommendation System: Movieblock
for UNIST CSE364 Software Engineering

## Milestone 2

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

The recommendation system takes 4 inputs, gender, age, occupation and genre(s), and the last one is optional.

First, the engine divides the users into 8 groups and count number of users belong to each group. In the table below, Y means that the user has same property with the input user, and N means not.

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

Then find the ratio of each group among all users and let's call this 'groupRatio'. groupRatio will have a value of 0 or more, but less than 1.

Now, iterate over ratings one by one and add score of the movie to corresponding variable. The score is slightly modified with a formula below before being added. `r` is the groupRatio of the group which the user made the rating belongs to.

![image](https://user-images.githubusercontent.com/6457345/116816565-6ead5300-ab9d-11eb-9cd9-6157eff6823f.png)

The weight will be closer to 1 if the group is larger, and close to e(=2.71828..., aka. Euler's number) if the group is smaller.

Sum up these weighted scores and divide them with the number of ratings of each movie to find the average.

What we think of in this algorithm is to emphasize evaluation of the user as there are fewer people who match the characteristics of the user given as input.

This recommendation system is in a situation where it is necessary to recommend movies only with the characteristics of the user without the user's previous viewing record or evaluation record data, which can be considered as a cold start. Therefore, we designed this algorithm because we had to find the biases of people that match the characteristics of the user given as input in the data we have, and we thought that movies that matches this should be recommended.

Finally, sort the movies in descending order of the average score, then return movie objects with top 10 average score. If any genre is specified, the movies with corresponding genre are returned.

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
