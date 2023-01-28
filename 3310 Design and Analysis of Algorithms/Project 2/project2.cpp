#include<iostream>

#include<algorithm>

using namespace std;

/* Algorithm1 - MergeSort */

int Algorithm1(int myArray[], int size, int kth)

{

sort(myArray, myArray+size);

return myArray[kth-1];

}

/* Algorithm 2a - QuickSort Iteratively */

/* partition2 declaration */

int partition2(int myArray[], int l, int r);

int Algorithm2a(int myArray[], int low, int high, int kth)

{

if (kth > 0 && kth <= high - low + 1)

{

int position = partition2(myArray, low, high);

if (position-low == kth-1)

{

return myArray[position];

low = position + 1;

}

else if (position-low > kth-1)

{

return Algorithm2a(myArray, low, position-1, kth);

high = position - 1;

}

else

{

return Algorithm2a(myArray, position + 1, high,

kth-position+1-1);

}

}

return INT_MAX;

}

/* Algorithm2b - QuickSort Recursively */

int Algorithm2b(int myArray[], int l, int r, int kth)

{

if (kth > 0 && kth <= r - l + 1)

{

int position = partition2(myArray, l, r);

if (position-l == kth-1)

return myArray[position];

if (position-l > kth-1)

return Algorithm2b(myArray, l, position-1, kth);

return Algorithm2b(myArray, position+1, r, kth-position+l-1);

}

return INT_MAX;

}

/* swap2 definition used in Algorithm2a and Algorithm2b */

void swap2(int *a, int *b)

{

int temporary = *a;

*a = *b;

*b = temporary;

}

/* partition2 definition - used in Algorithm2a and Algorithm2b */

int partition2(int myArray[], int l, int r)

{

int x = myArray[r], i = l;

for (int j = l; j <= r - 1; j++)

{

if (myArray[j] <= x)

{

/* calling swap2 method */

swap2(&myArray[i], &myArray[j]);

i++;

}

}

/* calling swap2 method */

swap2(&myArray[i], &myArray[r]);

return i;

}

/* Algorithm3 - QuickSort with mm rule */

int partition3(int myArray[], int l, int r, int kth);

/* findMedian3 method */

int findMedian3(int myArray[], int size)

{

sort(myArray, myArray+size);

return myArray[size/2];

}

/* Algorithm3 definition */

int Algorithm3(int myArray[], int l, int r, int kth)

{

if (kth > 0 && kth <= r - l + 1)

{

int size = r-l+1;

int i, median[(10+4)/5];

for (i=0; i<size/10; i++)

median[i] = findMedian3(myArray+l+i*5, 10);

if (i*5 < size)

{

median[i] = findMedian3(myArray+l+i*5, size%10);

i++;

}

int medOfMed=(i==1)?median[i-1]:Algorithm3(median,0,i-1,i/2);

int position = partition3(myArray, l, r, medOfMed);

if (position-l == kth-1)

return myArray[position];

if (position-l > kth-1)

return Algorithm3(myArray, l, position-1, kth);

return Algorithm3(myArray, position+1, r, kth-position+l-1);

}

return INT_MAX;

}

/* swap3 definition used in Algorithm3 */

void swap3(int *a, int *b)

{

int temporary = *a;

*a = *b;

*b = temporary;

}

/* partition3 definition used in Algorithm3 */

int partition3(int myArray[], int l, int r, int x)

{

int i;

for (i=l; i<r; i++)

if (myArray[i] == x) break;

/* calling swap3 method */

swap3(&myArray[i], &myArray[r]);

i = l;

for (int j = l; j <= r - 1; j++)

{

if (myArray[j] <= x)

{

/* calling swap3 method */

swap3(&myArray[i], &myArray[j]);

i++;

}

}

/* calling swap3 method */

swap3(&myArray[i], &myArray[r]);

return i;

}

/* Main method */

int main()

{

/* Here, Array size N = 10 */

int myArray[] = {13, 4, 6, 8, 20, 10, 11, 12, 13, 14};

/* size */

int size = sizeof(myArray)/sizeof(myArray[0]);

/* kth declaration */

int kth;

/* Getting kth value from the user */

cout<<"Enter kth: "<<endl;

cin>>kth;

/* calling functions and displaying outputs */

cout << "Algorithm1 - K'th smallest element is " << Algorithm1(myArray, size, kth) <<endl;

cout << "Algorithm2a - K'th smallest element is " << Algorithm2a(myArray, 0, size-1, kth) <<endl;

cout << "Algorithm2b - K'th smallest element is " << Algorithm2b(myArray, 0, size-1, kth) <<endl;

cout << "Algorithm3 - K'th smallest element is " << Algorithm3(myArray, 0, size-1, kth) <<endl;

getchar();getchar();return 0;

}