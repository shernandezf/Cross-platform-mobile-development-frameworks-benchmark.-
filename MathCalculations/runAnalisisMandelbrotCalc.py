import subprocess
import optparse
import matplotlib.pyplot as plt 

parse = optparse.OptionParser()

def get_arguments():
    parse.add_option("-l", "--language", dest="language", help="language you want the analisis to run: e.g: kotlin. If you want to run a general analysis type: all")
    (options, arguments) = parse.parse_args()
    return options 


def run_command(command):
    try:
        # Run the command and capture the output
        result = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        return result.stdout
    
    except subprocess.CalledProcessError as e:
        # If the command fails, print the error
        print("Error:", e.stderr)


opciones = get_arguments()
if opciones.language.lower()=="kotlin":
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:k.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    print("Kotlin average runtime for the mandelbrot set calculation problem ",average ,"milliseconds")
elif opciones.language.lower()=="javascript":
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:js.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    print("JavaScript average runtime for the mandelbrot set calculation problem " ,average ,"milliseconds")

elif opciones.language.lower()=="dart":
    average=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:d.c.1"))
    average=sum_calc/5
    average= round(average, 2)
    print("Dart average runtime for the mandelbrot set calculation problem " ,average ,"milliseconds")
    
elif opciones.language.lower()=="all":
    average_kotlin=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:k.c.1"))
    average_kotlin=sum_calc/5
    average_kotlin= round(average_kotlin, 2)

    average_dart=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:d.c.1"))
    average_dart=sum_calc/5
    average_dart= round(average_dart, 2)

    average_javaScript=0.0
    sum_calc=0.0
    for i in range(5):
        sum_calc+=float(run_command("docker run pelucapreb/tesis2024-dk:js.c.1"))
    average_javaScript=sum_calc/5
    average_javaScript= round(average_javaScript, 2)

    data = {'Kotlin':average_kotlin, 'Dart':average_dart, 'JavaScript':average_javaScript}
    languages = list(data.keys())
    averages = list(data.values())
    fig = plt.figure(figsize = (10, 5))
 
    # creating the bar plot
    plt.bar(languages, averages, color ='blue', 
            width = 0.4)
 
    plt.xlabel("Tested programming languages")
    plt.ylabel("Average millisenconds obtain in the test")
    plt.title("Average of programming languages performance in Mandelbrot set calculation ")
    plt.show()
else:
    print("Invalid option.")