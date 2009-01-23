/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package marytts.signalproc.analysis;

import java.io.IOException;

import marytts.util.io.MaryRandomAccessFile;


/**
 * 
 * A wrapper class for line spectral frequencies.
 * For Actual LSF analysis, check LsfAnalyser.
 * 
 * @author Oytun T&uumlrk
 */
public class Lsfs {
    public double[][] lsfs;
    public LsfFileHeader params;
    
    public Lsfs()
    {
        this("");
    }
    
    public Lsfs(String lsfFile)
    {
        readLsfFile(lsfFile);
    }
    
    public void readLsfFile(String lsfFile)
    {
        lsfs = null;
        params = new LsfFileHeader();
        
        if (lsfFile!="")
        {
            MaryRandomAccessFile stream = null;
            try {
                stream = params.readHeader(lsfFile, true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (stream != null)
            {
                try {
                    lsfs = LsfAnalyser.readLsfs(stream, params);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void writeLsfFile(String lsfFile)
    {   
        if (lsfFile!="")
        {
            MaryRandomAccessFile stream = null;
            try {
                stream = params.writeHeader(lsfFile, true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (stream != null)
            {
                try {
                    LsfAnalyser.writeLsfs(stream, lsfs);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public void allocate()
    {
        allocate(params.numfrm, params.dimension);
    }
    
    public void allocate(int numEntries, int dimension)
    {
        lsfs = null;
        params.numfrm = 0;
        params.dimension = 0;
        
        if (numEntries>0)
        {
            lsfs = new double[numEntries][];
            params.numfrm = numEntries;
            
            if (dimension>0)
            {
                params.dimension = dimension;
                
                for (int i=0; i<numEntries; i++)
                    lsfs[i] = new double[dimension];
            }
        }
    }
    
    public static void main(String[] args)
    {
        Lsfs l1 = new Lsfs();
        l1.params.dimension = 5;
        l1.params.numfrm = 1;
        l1.allocate();
        l1.lsfs[0][0] = 1.5;
        l1.lsfs[0][1] = 2.5;
        l1.lsfs[0][2] = 3.5;
        l1.lsfs[0][3] = 4.5;
        l1.lsfs[0][4] = 5.5;


        String lsfFile = "d:/1.lsf";
        l1.writeLsfFile(lsfFile);
        Lsfs l2 = new Lsfs(lsfFile);

        System.out.println("Test of class Lsfs completed...");
    }
}

