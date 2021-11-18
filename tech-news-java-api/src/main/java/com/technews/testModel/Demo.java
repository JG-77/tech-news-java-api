package com.technews.testModel;

import java.util.Objects;

public class Demo {
    private String name;
    private int age;
    //created contructor -> right click in class -> Generate -> select fields
    //has the same name as the class that it was derived from & that the method has NO return type
    public Demo(String name, int age) {
        this.name = name;
        this.age = age;
    }
    //Getters and setters access private variables using public get() and set() methods
    public String getName() {
        return name;
    }
    //If the method doesn't need to return a value, then we can use the void keyword in place of a return type
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //@Override annotation on a method, it means that the method is coming to the class from a superclass (or parent class)
    //equals() method to compare two hash codes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demo demo = (Demo) o;
        return age == demo.age && Objects.equals(name, demo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    //we can also override toString() to be able to view the value of an object within the log file
    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
