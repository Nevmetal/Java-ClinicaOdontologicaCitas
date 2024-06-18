/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicaodontologica;

import static java.awt.Frame.MAXIMIZED_BOTH;

/**
 *
 * @author William
 */
public class ClinicaOdontologica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Principal P = new Principal();
        P.setVisible(true);
        P.setExtendedState(MAXIMIZED_BOTH);
    }
    
}
