/*
 * Copyright (C) 2010 Cluster of Excellence, Univ. of Saarland
 * Minwoo Jeong (minwoo.j@gmail.com) is a main developer.
 * This file is part of "TopicSeg" package.
 * This software is provided under the terms of LGPL.
 */

package topicseg.model;

import org.apache.log4j.Logger;

/**
 * unisaar.topicseg.model::TopicTypeModel.java
 *
 * @author minwoo
 */
public class TopicTypeModel {

	private transient Logger logger = Logger.getLogger(TopicTypeModel.class);
	
	protected GammaCache gammaCache;
	protected double alpha;
	protected double beta;
	
	public TopicTypeModel() {
		gammaCache = new GammaCache();
	}
	
	public TopicTypeModel(double alpha, double beta) {
		gammaCache = new GammaCache();
		this.alpha = alpha;
		this.beta = beta;
	}
	
    public double logProb(double[] counts, double[] priors){
		double v = 0;
		
        double N = 0, P = 0;
        for (int i = 0; i < counts.length; i++) {
            N += counts[i]; P += priors[i];
            v += gammaCache.logGamma(counts[i] + priors[i]) - gammaCache.logGamma(priors[i]);
            if (counts[i] > 0)
            	v -= gammaCache.logGamma(counts[i]);
        }
        v += gammaCache.logGamma(N) + gammaCache.logGamma(P) - gammaCache.logGamma(N + P);
        
        return v;
    }

    public double logProb(int x1, int x2){
		double v = 0;
		
        v = gammaCache.logGamma(x1 + x2);
		if (x1 > 0)
			v -= gammaCache.logGamma(x1);
		if (x2 > 0)
			v -= gammaCache.logGamma(x2);
        v += gammaCache.logGamma(alpha + beta) - gammaCache.logGamma(x1 + x2 + alpha + beta);
        v += gammaCache.logGamma(x1 + alpha) + gammaCache.logGamma(x2 + beta) - gammaCache.logGamma(alpha) - gammaCache.logGamma(beta);
        
        return v;
    }

    public double getGlobalTypePrior() {
    	return alpha;
    }
    
    public double getLocalTypePrior() {
    	return beta;
    }
    
	
}
