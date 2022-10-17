package com;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        new Application().begin();
    }
    private void begin() {
        System.out.println("begin");

        Model model = new Model("test_model");

        // variable-wise possible values
        IntVar v1 = model.intVar("v1", new int[]{0, 100, 200, 300, 4500, 500});
        IntVar v2 = model.intVar("v1", new int[]{0, 100, 200, 3000, 400, 500});
        IntVar v3 = model.intVar("v1", new int[]{0, 400, 700, 300});


        // consolidate the variables
        IntVar[] vars = new IntVar[]{v1, v2, v3};

        model.sum(vars, "=", 1000).post();

        Solver solver = model.getSolver();

        List<Solution> solutions = solver.findAllSolutions();

        System.out.println("total solutions " + solutions.size());

        solutions.stream().forEach(solution -> {

            int v1_solutionValue = solution.getIntVal(v1);
            int v2_solutionValue = solution.getIntVal(v2);
            int v3_solutionValue = solution.getIntVal(v3);

            int sum_ofSolutionValues = v1_solutionValue + v2_solutionValue + v3_solutionValue;

            System.out.println(v1_solutionValue + "-" + v2_solutionValue + "-" + v3_solutionValue + " = " + sum_ofSolutionValues);

        });

    }
}
