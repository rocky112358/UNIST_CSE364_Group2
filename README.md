# Movie Recommendation System: Movieblock
for UNIST CSE364 Software Engineering

## Milestone 2

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

![image](https://user-images.githubusercontent.com/6457345/116813655-c80e8580-ab8f-11eb-9710-3e501ac261f0.png)

The weight will be closer to 1 if the group is larger, and close to e(=2.71828..., aka. Euler's number) if the group is smaller.

Sum up these weighted scores and divide them with the number of ratings of each movie to find the average.

What we think of in this algorithm is to emphasize evaluation of the user as there are fewer people who match the characteristics of the user given as input.

This recommendation system is in a situation where it is necessary to recommend movies only with the characteristics of the user without the user's previous viewing record or evaluation record data, which can be considered as a cold start. Therefore, we designed this algorithm because we had to find the biases of people that match the characteristics of the user given as input in the data we have, and we thought that movies that matches this should be recommended.

Finally, sort the movies in descending order of the average score, then return movie objects with top 10 average score. If any genre is specified, the movies with corresponding genre are returned.

---

## ~~Milestone 1~~ (Deprecated)

This program no longer behaves like **How to run the program** described below in the lastest commit. (as of May 2, 2021)

This version is tagged as "milestone-1" and you may checkout if you like.

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
