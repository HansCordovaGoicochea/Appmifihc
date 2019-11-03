package tesis.hyc.com.appmifihc.Clases;

import com.orm.SugarRecord;

public class Author extends SugarRecord {
    String fullName;
    int age;
    int income;  // This is a new field

    public Author() {
    }

    public Author(String fullName, int age, int income) {
        this.fullName = fullName;
        this.age = age;
        this.income = income;
    }
}