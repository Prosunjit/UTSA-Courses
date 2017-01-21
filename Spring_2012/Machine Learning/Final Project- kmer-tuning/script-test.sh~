#!/bin/bash

background_work(){
      training_positive_data=$1"_positive.txt";
      training_positive_fre=$1"_fre_positive.txt";
      training_pattern_data=$1"_input_pattern.txt";
      training_fre=$1"_fre.txt";
      testing_fre=$2"_fre.txt";
      #input_training_file=$1".txt";
      #input_testing_file=$2".txt";
      input_training_file=$1;
      input_testing_file=$2;
      training_arff=$1".arff";
      testing_arff=$2".arff";

      cat $input_training_file | head -1000 > $training_positive_data;

      java KMer $training_positive_data input_pattern.txt  $training_positive_fre;
      java FeatureSelector $training_positive_fre feature $3 > $training_pattern_data; 
      java KMer $input_training_file $training_pattern_data $training_fre; 
      java KMer $input_testing_file $training_pattern_data $testing_fre;
      java ArffWriter $training_fre " " meme.txt " " $training_arff;
      java ArffWriter $testing_fre " " meme.txt " " $testing_arff;

}
background_work_extended(){
      training_positive_data=$1"_positive.txt";
      training_positive_fre=$1"_fre_positive.txt";
      training_pattern_data=$1"_input_pattern.txt";
      training_fre=$1"_fre.txt";
      testing_fre=$2"_fre.txt";
      #input_training_file=$1".txt";
      #input_testing_file=$2".txt";
      input_training_file=$1;
      input_testing_file=$2;
      training_arff=$1".arff";
      testing_arff=$2".arff";

      cat $input_training_file | head -1000 > $training_positive_data;
      #echo $3;
      #kmer1=$(echo "java KMer " $training_positive_data " input_pattern.txt " $training_positive_fre" " $4 " "$5 " " $6);
      java KMer  $training_positive_data input_pattern.txt $training_positive_fre $4 $5  $6;
      java FeatureSelector $training_positive_fre feature $3 > $training_pattern_data; 
      #echo $kmer1;
      java KMer $input_training_file $training_pattern_data $training_fre $4 $5 $6;       
      #java KMer $input_testing_file $training_pattern_data $testing_fre $4 $5 $6;
      java ArffWriter $training_fre " " meme.txt " " $training_arff >faltu;
      #java ArffWriter $testing_fre " " meme.txt " " $testing_arff;

}



test_accuracy(){
      background_work $1 $2 $3;

      # using J48
	#java -cp weka.jar weka.classifiers.meta.FilteredClassifier -F "weka.filters.unsupervised.attribute.RemoveType -T string" -t TF_1.arff -W weka.classifiers.trees.J48
      #using NaiveBayes
	java -cp weka.jar weka.classifiers.meta.FilteredClassifier -F "weka.filters.unsupervised.attribute.RemoveType -T string" -t $training_arff -T $testing_arff -W weka.classifiers.bayes.NaiveBayes;

}

tune_accuracy(){ 
  # taking 6 param.
  t_train=$1;
  t_test=$2;
  t_fea_select=$3;
  t_output=$4;
  t_max_k_mer=$5;
  t_k_mer_start_pos=$6;
  t_k_mer_start_val=$7;
 
  background_work_extended $t_train $t_test $t_fea_select  $t_max_k_mer $t_k_mer_start_pos $t_k_mer_start_val;  
  echo "*************8working with " $1" "$2" "$3 " "$4" " $5 " "$6" "$7" "$8"**************";
  java -cp weka.jar weka.classifiers.meta.FilteredClassifier -F "weka.filters.unsupervised.attribute.RemoveType -T string" -t $training_arff -W weka.classifiers.functions.Logistic -x 10 -o -v;
}

do_challenge(){
      # Generate files for weka.
      background_work $1 $2 $3;
      # classify with weka.
      java -cp weka.jar weka.classifiers.meta.FilteredClassifier -F "weka.filters.unsupervised.attribute.RemoveType -T string" -t $training_arff -T $testing_arff -W weka.classifiers.bayes.NaiveBayes -p 1 -distribution > weka.output;
      # parse weka result for desired result.
      java ParseWekaOutput weka.output $4;
}

download_practice_files(){
      wget "http://cs.utsa.edu/~jruan/teaching/CS6243_spring_2012/FinalProject/practice/"$1;
      wget "http://cs.utsa.edu/~jruan/teaching/CS6243_spring_2012/FinalProject/practice/"$2;
}

download_challenge_files(){
      wget "http://cs.utsa.edu/~jruan/teaching/CS6243_spring_2012/FinalProject/challenge/"$1;
      wget "http://cs.utsa.edu/~jruan/teaching/CS6243_spring_2012/FinalProject/challenge/"$2;
 }

check_practice_accuracy(){
      download_practice_files $1 $2;
      test_accuracy $1 $2 $3;
}
take_challenge(){
      download_challenge_files $1 $2;
      do_challenge $1 $2 $3 $4;
}
tune(){

      download_challenge_files $1 $2;
      tune_accuracy $1 $2 $3 $4 $5 $6 $7;
}

entry(){
      if [ $4 = "challenge" ]
	  then take_challenge $1 $2 $3 $5;
      elif [ $4 = "practice" ]
	  then check_practice_accuracy $1 $2 $3;
      elif [ $4 = "tune" ]
	  then tune $1 $2 $3 $5 $6 $7 $8;
      fi
  
}
# parameters = First or $1: training file, $2: testing file, $3: number, $4: practice/challenge, $5: challenge_output_file_name
# $6 = max-mer, $7 = mismatch starts from ; $8 = mismatch_start_with_value 
#entry $1 $2 $3 $4 $5 $6 $7;
#./script-test.sh TF_1_data_1.txt TF_1_data_2.txt 700 tune out_4_4_0 5 5 1

#./script-test.sh TF_11_data_1.txt TF_11_data_2_forPred.txt TF_11_data_1.txt.out tune 200 4 4 0 >>TF_11.out

entry $1 $2 $5 $4 $3 $6 $7 $8