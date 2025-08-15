package organisation.structure.exercise;

import organisation.structure.exercise.controller.IOrganizationalAnalysisController;
import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.view.IOrganizationalAnalysisView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ExerciseApplication implements CommandLineRunner {

	@Autowired
	private IOrganizationalAnalysisController controller;
	
	@Autowired
	private IOrganizationalAnalysisView view;

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			controller.getUsageInstructions();
			return;
		}

		String csvFilePath = args[0];
		AnalysisResult result = controller.analyzeOrganization(csvFilePath);
		
		if (!result.isSuccess()) {
			view.displayError("Analysis failed: " + result.getErrorMessage());
		} else {
			view.displaySuccess("Analysis completed successfully");
		}
	}
}
