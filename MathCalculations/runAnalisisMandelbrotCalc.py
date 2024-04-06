import subprocess
import optparse
import matplotlib.pyplot as plt 
import random
parse = optparse.OptionParser()

def get_arguments():
    parse.add_option("-l", "--language", dest="language", help="language you want the analisis to run: e.g: kotlin. If you want to run a general analysis type: all")
    (options, arguments) = parse.parse_args()
    return options 


def run_command(command):
    try:
        result = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        return result.stdout
    
    except subprocess.CalledProcessError as e:
        print("Error:", e.stderr)

def run_kotlin():
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:k.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    return average

def run_dart():
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:d.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    return average

def run_javaScript():
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:js.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    return average

opciones = get_arguments()
if opciones.language.lower()=="kotlin":
    averageK=run_kotlin()
    print("Kotlin average runtime for the mandelbrot set calculation problem ",averageK ,"milliseconds")
elif opciones.language.lower()=="javascript":
    averageJ=run_javaScript()
    print("JavaScript average runtime for the mandelbrot set calculation problem " ,averageJ ,"milliseconds")

elif opciones.language.lower()=="dart":
    averageD=run_dart()
    print("Dart average runtime for the mandelbrot set calculation problem " ,averageD ,"milliseconds")
    
elif opciones.language.lower()=="all":
    average_kotlin=0
    average_dart=0
    average_javaScript=0
    list_functions=[run_kotlin,run_javaScript,run_dart]
    random.shuffle(list_functions)
    for i in list_functions:
        if i == run_kotlin:
            average_kotlin=i()
        elif i == run_javaScript:
            average_javaScript=i()
        elif i == run_dart:
            average_dart=i()  
    data = {'Kotlin':average_kotlin, 'Dart':average_dart, 'JavaScript':average_javaScript}
    languages = list(data.keys())
    averages = list(data.values())
    fig = plt.figure(figsize = (10, 5))
 
    # creating the bar plot
    plt.bar(languages, averages, color ='blue', 
            width = 0.4)
 
    plt.xlabel("Tested programming languages")
    plt.ylabel("Average millisenconds obtain in the test")
    plt.title("Average of programming languages performance in the mandelbrot set calculation problem ")
    plt.show()
else:
    print("Invalid option.")