/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.interfaces;

/**
 *
 * @author ghpm9
 */
public interface LoadingInstanceListener {
    
    public void notify(String msg);
    
    public void completed();
}
