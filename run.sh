git clone https://github.com/rocky112358/UNIST_CSE364_Group2.git
cd UNIST_CSE364_Group2
mvn package
echo ""
mongod --fork --dbpath=/var/lib/mongodb --logpath=/var/log/mongod.log
java -jar target/ROOT-jar-with-dependencies.jar
