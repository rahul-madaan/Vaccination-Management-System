from os import walk

f = []
for (dirpath, dirnames, filenames) in walk("E:/Downloads/archive/samples"):
    f.extend(filenames)
    break
for x in range(1070):
    print(str(f[x])[0:5])