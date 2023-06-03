# Kernel Simulator

This is a kernel simulator that simulates the behavior of kernel OS. It is written in Java and utilizes Java standards. The simulator is able to simulate the following kernel functions:

- Process creation
- Process preemption
- Process resumption
- Process termination
- Process state transitions
- Process execution
- Process I/O handling

___

## Main Features


- Processes are spawned using their source code and customizable arrival time. The source code is written in a specific syntax, illustrated in the processes files, and is parsed by the simulator to simulate the processes accordingly.

- Round Robin scheduling algorithm is implemented to schedule the processes with a customizable time slice (aka quantum).

- The simulator is able to simulate the following process states: NEW, READY, RUNNING, BLOCKED, FINISHED.

- Physical memory is simulated repecting memory management principles.

- Virtual memory is simulated by swapping in and out process to hard disk.

- Resource management and System Calls are simulated by allocating and deallocating resources to processes by the help of MUTEXes (aka Locks).

- The simulator's architecture is designed in a way that abides by the OS core design principles.

## Contributors

#### This project has been implemented by a team of five computer engineering students

- [Ahmad Hoseiny](https://github.com/AhmadHoseiny)
- [Omar Wael](https://github.com/o-wael)
- [Abdelrahman Salah](https://github.com/19AbdelrahmanSalah19)
- [Ali Hussein](https://github.com/AliAdam102002)
- [Logine Mohamed](https://github.com/logine20)