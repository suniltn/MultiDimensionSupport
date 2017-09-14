# MultiDimensionSupport
This Repo host a tool that can help you to generate the Dimens.xml for multiple screens.

Android Way:

Android can have multiple values folder based on screen dimensions and size. The utility file below can auto generate the multiple dimen files based on your choice. With this tool you can generate dimens.xml for all screen densities and sizes. 


 1. Place the souce file in the  Project Home folder 




2. add below code to build.gadle , This code assumes  values-sw1200dp as the base version and creates other dimen files

task generateDimensReource (type: Exec) {
    println "@@@start to run dimens_tools!!!!"
    inputs.files 'src/main/res/values-sw1200dp/dimens.xml'
    workingDir "../dimens_tools"
    commandLine 'java', 'DimenConverterTool'
}

PS:
If not for android , you can use this as an independent Java application
You can use your terminal/Shell to compile and excecute using #javac  and #java comands.
