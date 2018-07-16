# gnuplot -e "input='./savedresults/curiosiphi/'" scripts/completeness.gnuplot

set datafile separator ","
set title 'Query Completeness by Round'                       # plot title
set xlabel 'Round'                              # x-axis label
set ylabel 'Query Completeness (%)'                          # y-axis label
set terminal png size 800,600
set style data linespoints

location = input

# RPS only plots
rps = location.'snob-*-rps.txt*'
SIZE = system("ls -1 " . rps . "| wc -l")
FILES = system("ls -1 " . rps)
set xrange [1:100]
print 'Size:', SIZE
if (SIZE > 0) {
    set output location.'rps-completeness.png'
    plot for [file in FILES] file using 1:7 lw 2 title file
    set output location.'rps-messages.png'
    plot for [file in FILES] file using 1:8 lw 2 title file
}

# RPS+SON plots
son = location.'snob-*-rps+son.txt*'
SIZE = system("ls -1 " . son . "| wc -l")
FILES = system("ls -1 " . son)
set xrange [1:100]
print 'Size:', SIZE
if (SIZE > 0) {
    set output location.'rps+son-completeness.png'
    plot for [file in FILES] file using 1:7 lw 2 title file
    set output location.'rps+son-messages.png'
    plot for [file in FILES] file using 1:8 lw 2 title file
}
