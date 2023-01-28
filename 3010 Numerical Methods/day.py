import numpy as np
import math

## a function returns the Gradient vector at the input vector x_i
def getGradientVector(x_i):
    g = np.zeros((2,1))
    g[0] = 2*x_i[0] - 4 - x_i[1] # 2*x1 - 4 - x2
    g[1] = 2*x_i[1] - 4 - x_i[0] # 2*x2 - 4 - x1
    return g

# a simple function returns the norm of an input vector
def getNorm(v):
    return math.sqrt(v[0]*v[0]+v[1]*v[1])

## set an initial guess
x_0 = np.array([[1], [1]])
print(f"--The initial guess x0: ")
print(x_0)

## set a threshold value for getting a good solution
thresh = 0.01

## set step length 
alpha = 1

x_i = x_0
count = 0
while True:
    print('****************************************************')
    print(f'--At iteration: {count}')
  
    ## compute the gradient vector at x_i
    g = getGradientVector(x_i)
    print(f"--The gradient vector g(x_{count}): ")
    print(g)

    ## compute the norm of the gradient vector at x_i
    normOfGradient = getNorm(g)
    print(f"--The norm of the gradient vector |g(x_{count})|: {normOfGradient}")

    ## check if converged; if true, then stop; continue otherwise
    if normOfGradient <= thresh:
        break

    ## get h=-g
    h_i = -1*g
    print(f"--The h vector h_{count}: ")
    print(h_i)

    ## get the new solution point
    x_i_plus1 = x_i + alpha * h_i
    print(f"--The solution vector x_{count+1}: ")
    print(x_i_plus1)

    x_i = x_i_plus1
    count = count + 1
    print()
