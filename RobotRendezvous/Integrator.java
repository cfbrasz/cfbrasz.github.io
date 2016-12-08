
public interface Integrator  
{
    /** 
     * Advances one time step
     */
    public void advanceTime(double dt);
    public void setdt(double dt);
}