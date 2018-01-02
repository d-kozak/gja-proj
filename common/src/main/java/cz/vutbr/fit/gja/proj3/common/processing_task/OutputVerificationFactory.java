package cz.vutbr.fit.gja.proj3.common.processing_task;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.OutputVerificationStrategy;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputVerificationDTO;

import java.io.File;

public class OutputVerificationFactory {

    private OutputVerificationFactory() {
        throw new RuntimeException("No instance class");
    }

    public static OutputVerificationStrategy strategyFor(OutputVerificationDTO outputVerificationDTO) {
        switch (outputVerificationDTO.getOutputType()) {
            case NO_CHECK:
                return (fileNum) -> true;
            case SINGLE_FILE:
                return checkSingleFile(outputVerificationDTO);
            case ENUMERATED_LIST:
                return checkEnumeratedList(outputVerificationDTO);
            case REGEX:
                return checkRegex(outputVerificationDTO);
            default:
                throw new RuntimeException("default at switch");
        }
    }

    private static OutputVerificationStrategy checkRegex(OutputVerificationDTO outputVerificationDTO) {
        return (fileNum) -> {
            if (outputVerificationDTO.getExpectedOutput()
                                     .size() == 0) {
                throw new RuntimeException("one regex needed");
            }
            String regex = outputVerificationDTO.getExpectedOutput()
                                                .get(0);

            File dir = new File(outputVerificationDTO.getOutputDirectory());
            if (!dir.exists() || !dir.isDirectory())
                return false;
            File[] files = dir.listFiles(file -> file.getName()
                                                     .matches(regex));
            return files != null && files.length == fileNum;
        };
    }

    private static OutputVerificationStrategy checkEnumeratedList(OutputVerificationDTO outputVerificationDTO) {
        return (fileNum) -> {
            if (outputVerificationDTO.getExpectedOutput()
                                     .size() == 0) {
                throw new RuntimeException("empty list of output files");
            }
            File directory = new File(outputVerificationDTO.getOutputDirectory());
            if (!directory.exists() || !directory.isDirectory())
                return false;
            return outputVerificationDTO.getExpectedOutput()
                                        .stream()
                                        .map(name -> name.replace("{fileNum}", fileNum + ""))
                                        .map(name -> new File(directory.getPath() + File.separator + name))
                                        .allMatch(File::exists);
        };
    }

    private static OutputVerificationStrategy checkSingleFile(OutputVerificationDTO outputVerificationDTO) {
        return (fileNum) -> {
            if (outputVerificationDTO.getExpectedOutput()
                                     .size() == 0) {
                throw new RuntimeException("No output file specified");
            }
            File directory = new File(outputVerificationDTO.getOutputDirectory());
            if (!directory.exists() || !directory.isDirectory())
                return false;
            String fileName = outputVerificationDTO.getExpectedOutput()
                                                   .get(0)
                                                   .replace("{fileNum}", fileNum + "");
            File file = new File(directory + File.separator + fileName);
            return file.exists();
        };
    }
}
