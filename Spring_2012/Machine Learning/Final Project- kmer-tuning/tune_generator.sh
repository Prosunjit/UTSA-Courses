#!/bin/bash
cmd_option="tune";
count=10;
while read line
do 
  
 count=`expr $count + 1`
  while read tune_para
  do
    cmd=$(echo -e  "./script-test.sh " $line" " $cmd_option " "$tune_para ">>TF_"$count".out");
     #echo "echo \"================= working with $cmd =================>>TF_"$count".out\"";
    echo $cmd;
   
  done < $2;
    echo -e "\n";
done < $1