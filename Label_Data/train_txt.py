import os

# root folder
path_dir = "C:/Users/Lee/Desktop/d"
# folder list
# ex) apple, arm, bird etc...
file_list = os.listdir(path_dir)


print(file_list)

# write train_txt path
f = open("C:/Users/Lee/Desktop/train.txt", 'a')
for i in file_list:
    for j in range(400):
        path_txt = path_dir+"/"+i+"/"+str(j)+".txt"
        f.write(path_txt+"\n")

f.close()