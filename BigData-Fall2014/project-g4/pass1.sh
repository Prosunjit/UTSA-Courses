#!/bin/bash
# how to run: ./pass1.sh <datasetname>
# dataset names are following: cit, soc, web, wiki


inputDir=$1

#find . | grep java| xargs javac -classpath /usr/local/hadoop-1.2.1/hadoop-core-1.2.1.jar -d pagerank_classes/ 



#jar -cvf pagerank.jar -C pagerank_classes/ .




echo "output format:"
echo "*** Data   Iteration	Time (second)***"
cumulativeTime=0

for i in `seq 1 3`;
do
	iteration=$i
	#echo "Running dataset "$inputDir  " for iteration "$iteration
	x=$RANDOM

	START=$(date +%s)

	$HADOOP_PREFIX/bin/hadoop jar  /usr/local/hadoop-1.2.1/project/tmp_repo/project-g4/pagerank.jar com.xebia.sandbox.hadoop.WikiPageRanking /user/ubuntu/g4/$inputDir /user/ubuntu/g4/$inputDir/output-$RANDOM $iteration /user/ubuntu/result/$RANDOM &>$x

	END=$(date +%s)
	DIFF=$(( $END - $START ))
	cumulativeTime=$((cumulativeTime + DIFF))
#cat $x | tail -n 10 | head -n 1 | cut -c 66- 

	echo $1 "   " $iteration "  "$cumulativeTime
	rm $x 
done
