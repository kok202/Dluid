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

For example, there is a xor dataset below.  
|제목|내용|설명|
|:------:|:---:|:---:|
|테스트1|테스트2|테스트3|
|테스트1|테스트2|테스트3|
|테스트1|테스트2|테스트3|