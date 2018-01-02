package cz.vutbr.fit.gja.proj3.common.processing_task.control;

@FunctionalInterface
public interface OutputVerificationStrategy {
    boolean verify(int fileNum);
}
