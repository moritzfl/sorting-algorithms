package de.moritzf.sorting.logic.sorting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The type Sorting algorithm.
 */
public abstract class SortingAlgorithm {


    /**
     * Executes all steps of the algorithm.
     */
    public abstract void doAllSteps();

    /**
     * Executes one step of the algorithm.
     *
     * @return true if successful.
     */
    public abstract boolean doStep();

    /**
     * Gets the size of the array that was used as input.
     *
     * @return the input size
     */
    public abstract int getInputSize();


    /**
     * Resets the algorithm to the start and removes
     * any information previously generated during execution.
     */
    public abstract void reset();

    /**
     * Generates a LaTeX-expression representing the steps that were performed.
     *
     * @return the latex expression.
     */
    public abstract String protocol2LaTeX();


    /**
     * Reverts the last step performed by the algorithm.
     *
     * @return the boolean
     */
    public abstract boolean undoStep();

    /**
     * Gets name of the algorithm
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets step limit. Used to limit the maximum number of steps that can be performed at once.
     * This is used when a long protocol is expected due to a large input and replaces the execute all steps option in the gui.
     *
     * @return the step limit
     */
    public abstract int getStepLimit();


    public String getPdfInfoFilePath() {
        try {
            return this.getResourceAsFile(this.getName().toLowerCase() + ".pdf").getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    private File getResourceAsFile(String resourcePath) throws IOException {

        InputStream in = this.getClass().getResourceAsStream(resourcePath);

        if (in == null) {
            throw new IOException("Resource not found.");
        }

        File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".pdf");

        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            //copy stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }


}