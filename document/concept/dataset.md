---
title: Dataset
parent: Concept
has_children: false
nav_order: 2
---

## Dataset

There are so different interpretations of what a `dataset` is. 
Someone call dataset is feature set, but other call it result set, and the other call it feature + result set! 
So in this application we need to declare what a `dataset` is.

Dluid calls the dataset is a bundle of (`feature set` + `result set`). And `feature` and `result` is a `record set`.
````
DataSet : FeatureSet + ResultSet
FeatureSet is RecordSet
ResultSet is RecordSet
RecordSet is set of record
Record is one row in table.
````

For example, there is a xor data set below.  

|a|b|a xor b|
|:---:|:---:|:---:|
|0|0|0|
|0|1|1|
|1|0|1|
|1|1|0|

In this case feature set is. 

|a|b|
|:---:|:---:|
|0|0|
|0|1|
|1|0|
|1|1|

And result set is. 

|a xor b|
|:---:|
|0|
|1|
|1|
|0|

And record is

|a|b|a xor b|
|:---:|:---:|:---:|
|0|0|0|

|a|b|a xor b|
|:---:|:---:|:---:|
|0|1|1|

|a|b|a xor b|
|:---:|:---:|:---:|
|1|0|1|

|a|b|a xor b|
|:---:|:---:|:---:|
|1|1|1|

And record set is collection of record.
So `data set`, `feature set` and `result set` are sub type of record set.

#### * Origin of sample datasource 
| |link|
|:---:|:---|
|iris|https://github.com/deeplearning4j/dl4j-examples/tree/master/dl4j-examples/src/main/resources|
|housing|https://github.com/chendaniely/pandas_for_everyone/tree/master/data|
|wine|https://github.com/chendaniely/pandas_for_everyone/tree/master/data|
|stock|https://github.com/chendaniely/pandas_for_everyone/tree/master/data|
|mnist|dataset in dl4j.|
|Stock train data|extracted date from 2014-07-24 to 2016-08-03|  
|Stock test data|extracted date from 2016-08-04 to 2016-08-25|
