#include <bits/stdc++.h>
using namespace std;


void INPUT()
{ 
#ifndef ONLINE_JUDGE
  freopen("C:/Users/arvindersingh/Desktop/Current/input.txt", "r", stdin);
  freopen("C:/Users/arvindersingh/Desktop/Current/output.txt", "w", stdout);
#endif
}

class Student
{
	public:
	string rollNo;
	string Name;
	int OOPS, OS, DBMS, CN;
	Student(string rollNo,string Name, int OOPS,int OS,int DBMS,int CN){
		this->rollNo = rollNo;
		this->Name   = Name;
		this->OOPS	 = OOPS;
		this->OS     = OS;
		this->DBMS   = DBMS;
		this->CN     = CN;
	};
		
};


bool valid(const Student &cur, int maxMarks){
	return  (
						(cur.OOPS>=0 && cur.OOPS <=maxMarks) &&
						(cur.OS>=0 && cur.OS <=maxMarks) &&
						(cur.DBMS>=0 && cur.DBMS <=maxMarks) &&
						(cur.CN>=0 && cur.CN <=maxMarks)
					);
}

bool withinRange(const Student &cur){
	return  (
						(cur.OOPS > 70) ||
						(cur.OS>70 ) ||
						(cur.DBMS>70) ||
						(cur.CN>70)
					);
}

int main(int argc, char const *argv[])
{

	//INPUT();

	cout<<"Enter maximum marks :";
	int maxMarks;
	cin>>maxMarks;
	cout<<"Enter number of students :";
	int n;
	cin>>n;
	vector<Student> v;
	cout<<"Enter details of " <<n <<" students \n(Roll No,Name, Marks_OOPS, Marks_OS,Marks_DBMS,Marks_CN):\n\n";
	for (int i = 0; i < n; ++i)
	{
		string rollNo;
		string Name;
		int OOPS, OS, DBMS, CN;
		cin>>rollNo>>Name>>OOPS>>OS>>DBMS>>CN;
		v.push_back(Student(rollNo,Name,OOPS,OS,DBMS,CN));
	}

	vector<Student> res;
	for(auto cur:v){
		if(valid(cur,maxMarks) && withinRange(cur)){
			res.push_back(Student(cur));
		}
	}

	cout<<"\nNumber of students who have scored more than 70  in any one subjects: "<<res.size();
	cout<<"\nList of students who have scored more than 70  in any one subjects: "<<endl;
	for(auto cur:res){
		cout<<cur.rollNo<<"  "<<cur.Name<<endl;
	}

	return 0;
	
}

/*
INPUT:

100
10
18124001 Adarsh 65 68 69 55
18124002 Aditya 72 84 65 68
18124003 Akhil 65 65 62 50
18124004 Arvinder 82 88 84 78
18124005 Ashi 74 78 70 72
18124006 Ashita 85 87 88 75
18124007 Ashutosh 72 71 78 85
18124008 Avani 78 65 68 71
18124009 Bindeshwari 68 82 71 66
18124010 Cherring 68 64 65 66

*/


