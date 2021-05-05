Psychological-Projective-Drawings-analysis-app<a name="TOP"></a>
===================

Psychological-Projective-Drawings-analysis-app is psychological test & drawing journal application.

It is written in Java and built on android studio.

## Main features
* Supports google login
* Take a test of HTP
* Get analysis result of the test by drawn images
* Alert when the analysis result is beyond the normal range
* Search nearby hospital when pressed searching button on alert page
* Write an journal & draw a picture which is relevant to the journal
* Save drawn images on phone
* Look through a previously written journals searching by date


## Build from source
* Android gradle plugin: 3.6.2
* Gradle: 5.6.4


## Screenshots
[<img src="https://user-images.githubusercontent.com/64185453/101239481-2ba09e00-372b-11eb-9e71-2aaa15e1f219.jpg" width=160>](https://user-images.githubusercontent.com/64185453/101239481-2ba09e00-372b-11eb-9e71-2aaa15e1f219.jpg)
[<img src="https://user-images.githubusercontent.com/64185453/101239492-41ae5e80-372b-11eb-88ec-cf5b8e35fa14.jpg" width=160>](https://user-images.githubusercontent.com/64185453/101239492-41ae5e80-372b-11eb-88ec-cf5b8e35fa14.jpg)
[<img src="https://user-images.githubusercontent.com/64185453/101239473-23e0f980-372b-11eb-9bee-b8554efc17d0.jpg" width=160>](https://user-images.githubusercontent.com/64185453/101239473-23e0f980-372b-11eb-9bee-b8554efc17d0.jpg)
[<img src="https://user-images.githubusercontent.com/64185453/101239487-39562380-372b-11eb-9664-37dfc12e9eda.jpg" width=160>](https://user-images.githubusercontent.com/64185453/101239487-39562380-372b-11eb-9664-37dfc12e9eda.jpg)
[<img src="https://user-images.githubusercontent.com/64185453/101239467-1af02800-372b-11eb-89af-c74eab7bc88f.jpg" width=160>](https://user-images.githubusercontent.com/64185453/101239467-1af02800-372b-11eb-89af-c74eab7bc88f.jpg)



## Model

Model Name: darknet (yolov4)

Reference: https://github.com/AlexeyAB/darknet

Environment
* OS: linux (Ubuntu 18.04 LTS)
* GUP: Titan RTX

Requirements
* cmake >= 3.18
* OpenCV >= 2.4
* cuDNN >= 8.0.2
* CUDA >= 10.2

1. Create file yolo-obj.cfg with the same content as in yolov4-custom.cfg (or copy yolov4-custom.cfg to yolo-obj.cfg): [yolo-obj.cfg](./files/yolo-obj.cfg)
2. Create file obj.names in the directory build\darknet\x64\data\, with objects names - each in new line: [obj.names](./files/obj.names)
3. Create file obj.data in the directory build\darknet\x64\data\, containing (where classes = number of objects): [obj.data](./files/obj.data)
4. Put image-files (.jpg) of your objects in the directory build\darknet\x64\data\obj\:  
5. You should label each object on images from your dataset. Use this visual GUI-software for marking bounded boxes of objects and generating annotation files for Yolo v2 & v3 :  https://github.com/tzutalin/labelImg 
* dataset: [Img.zip](./Label_Data/Img.zip)
7. Create file train.txt in directory build\darknet\x64\data\, with filenames of your images, each filename in new line, with path relative to darknet.exe, for example python code containing: [train.txt](./files/train.txt) 
* python code : [train_txt.py](./Label_Data/train_txt.py) & [test_txt.py](./Label_Data/test_txt.py)
9. To train on Linux use command: ./darknet detector train data/obj.data yolo-obj.cfg yolov4.conv.137 (just use ./darknet instead of darknet.exe)
10. After training is complete - get result yolo-obj_final.weights from path build\darknet\x64\backup\ : [weight](https://drive.google.com/drive/folders/1w71kSiztS5bpGLLQyvpZTCDcB4-kQAho?usp=sharing)



* Loss Function image

[<img src="https://user-images.githubusercontent.com/57584426/116976606-5acc3300-acfc-11eb-93c4-44cafbdad78c.png" width=500>](https://user-images.githubusercontent.com/64185453/101239467-1af02800-372b-11eb-89af-c74eab7bc88f.jpg)


## Android
### Devolopment Environment
- Android Studio 4.1.1

### Application Version
- minSdkVersion 25
- targetSdkVersion 29

### Firebase
#### 1. Connect to Firebase
(You can also check this out in the official [Firebase documentation](https://firebase.google.com/docs/android/setup).)
1) Sign into [Firebase](https://firebase.google.com/?hl=ko) using your Google account.
2) Create a Firebase project.
3) Register this app(in this [folder](./SP_SeniorProject)) with Firebase.<br>
   <u>***ADD SHA-1 key***</u><br>
   (1) Open Android Studio<br>
   (2) Click the Gradle button in the upper right.<br>
   (3) app -> Tasks -> android -> signingReport<br>
   (4) Copy **SHA1**<br>
4) Add a Firebase configuration file

#### 2. Authentication - Google Login
After signing up/logging in, you can check uer information in [Firebase](https://firebase.google.com/) -> your project -> Authentication -> Users.<br>
Using this function, store user information and get UID.<br>

#### 3. Realtime Database
Using Realtime DB to store Test Result.<br>

```
└── project
    ├── TestData
    │   └── UID
    │       └── data (yyyyMMdd_)
    │           ├── data: date of test
    │           ├── resultSentence: analysis result
    │           ├── score: HTP score
    │           ├── sentimentWord: analysis result
    │           └── type: house, tree, person
    └── ScroreData
        └── UID
            └── year
                └── month
                    └── date
                        └── date
                        └── score: 0~13
```
