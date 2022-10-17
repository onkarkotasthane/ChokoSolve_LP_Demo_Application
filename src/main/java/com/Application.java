package com;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Choco Solver LP Sample demo application
 * */
public class Application {
    Logger logger = Logger.getLogger(this.getClass().toString());

    public static void main(String[] args) {
        new Application().begin();
    }
    private void begin() {

        // define LP model
        Model model = new Model("test_model");

        // variable space
        IntVar v1 = model.intVar("v1", new int[]{0, 100, 200, 300, 4500, 500});
        IntVar v2 = model.intVar("v1", new int[]{0, 100, 200, 3000, 400, 500});
        IntVar v3 = model.intVar("v1", new int[]{0, 400, 700, 300});

        // consolidated variables array
        IntVar[] vars = new IntVar[]{v1, v2, v3};

        /*  defining operation with condition value.
            condition: sum of all three variables (i.e. v1, v2 and v3) must be equal to the given condition value (i.e.1000)
         */
        final int conditionValue = 1000;
        final String operation = "="; // operation to be perform (i.e.'=', '<', '>', '<=', '>=')
        model.sum(vars, operation, conditionValue).post();

        // execute the expression
        Solver solver = model.getSolver();

        // get all feasible solutions
        List<Solution> solutions = solver.findAllSolutions();
        int totalSolutionsCount = solutions.size();
        logger.log(Level.INFO, "total solutions {0}", totalSolutionsCount);

        if(totalSolutionsCount == 0) {
            logger.log(Level.WARNING, "No solution(s) found for the given input for LP");
        } else {
            // iterating over solutions for doing more operations
            solutions.stream().forEach(solution -> {
                int v1SolutionValue = solution.getIntVal(v1);
                int v2SolutionValue = solution.getIntVal(v2);
                int v3SolutionValue = solution.getIntVal(v3);
                int sumOfSolutionValues = v1SolutionValue + v2SolutionValue + v3SolutionValue;
                logger.log(Level.INFO, "v1:{0}, v2:{1}, v2:{2} = {3}", new Object[]{v1SolutionValue,v2SolutionValue,v3SolutionValue,sumOfSolutionValues});
            });
        }
    }
}
