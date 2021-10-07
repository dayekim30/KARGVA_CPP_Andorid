#include <jni.h>
#include <string>
#include <fstream>
#include <iostream>
#include <zlib.h>
#include <fcntl.h>
#define _CRT_SECURE_NO_DEPRECATE
#include <chrono>
#include <unordered_map>

#include "headers/Parsing.h"

using namespace std;
using namespace std::chrono;
using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_ops_adictest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_ops_adictest_MainActivity_ReadFile(
        JNIEnv* env,
        jobject /* this */) {
    Parsing* parsing = new Parsing(9);
    parsing->Aread("/data/data/com.ops.adictest/results/kargva_db_v5.fasta");
    auto start = high_resolution_clock::now();
    parsing->Qread("/data/data/com.ops.adictest/results/testdata_simul.fastq" , true);
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);
    cout << "this is the second : " << duration.count() << endl;
   ifstream MyFile("/data/data/com.ops.adictest/files/example.txt");
    string hello = "";
    string line;
    if (MyFile.is_open())
    {
        while ( getline (MyFile,line) )
        {
            hello = hello+line;
        }
        MyFile.close();
    }
    fstream* i_nf = new fstream();
    i_nf->open("/data/data/com.ops.adictest/results/testdata_simul.fastq", ios::in);

    /*
    fstream fout;
    fout.open("/data/data/com.ops.adictest/files/result.csv", ios::out | ios::app);
    fout << "name, id \n";
    if (i_nf->is_open()) {
        string tp;

        while (getline(*i_nf, tp)) {
            if (tp[0] != '@') {
                std::cout << "this is wrong FASTQ file format." << endl;
                exit(0);
            }
            fout << tp;
            getline(*i_nf, tp);
            fout << "," << tp << "\n";
            getline(*i_nf, tp);
            getline(*i_nf, tp);

        }
        fout.close();
        i_nf->close();

    }*/
    return env->NewStringUTF(line.c_str());
}