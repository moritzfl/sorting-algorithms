package logic.sorting;

public abstract class SortingAlgorithm {
	
	public abstract void doAllSteps();
	
	public abstract boolean doStep();
	
	public abstract int getInputSize();
	
	public abstract int getProtocolSize();

	public abstract void reset();
	
	public abstract String protocol2LaTeX();
	
	public abstract String step2LaTeX(int i);
	
	public abstract boolean undoStep();
	
	public abstract String getName();

	public abstract int getStepLimit();

	
	
}