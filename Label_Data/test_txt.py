import os

# root folder
path_dir = "C:/Users/Lee/Desktop/d"
# folder list
# ex) apple, arm, bird etc...
file_list = os.listdir(path_dir)


print(file_list)

# write train_txt path
f = open("C:/Users/Lee/Desktop/test.txt", 'a')
for i in file_list:
    txt_list = os.listdir(path_dir+"/"+i)

    for j in range(400,len(txt_list)-1):
        path_txt = path_dir+"/"+i+"/"+str(j)+".txt"
        f.write(path_txt+"\n")

f.close()