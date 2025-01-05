package com.example.my_course.sqlandother;

public class Trial {
    public boolean search(int arr [], int index) {
        // Check if index is within bounds
        for (int i=0; i<arr.length;i++){
            if(arr[i]==index){
                return true;// Return true if target is found
            }
        }
        return false;// Return false if target is found
    }

    public static void main(String[] args) {
        int arr[] = {8,2,1,9,5};
        Trial example = new Trial();

        System.out.println(example.search(arr,2));
        System.out.println(example.search(arr,4));
        System.out.println(example.search(arr, 5));
    }
}
