package com.gustavonalle.uima.app;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.conceptMapper.DictTerm;
import org.apache.uima.jcas.JCas;
import org.uimafit.util.JCasUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import static org.uimafit.factory.AnalysisEngineFactory.createAnalysisEngine;

public class App {


  public static AnalysisEngine createCommodityEngine() throws IOException, UIMAException {
    return createAnalysisEngine("OffsetTokenizerMatcher");
  }

  public static void main(String[] args) throws UIMAException, IOException {

    AnalysisEngine commodityEngine = createCommodityEngine();
    JCas jCas = commodityEngine.newJCas();


    Scanner scanner = new Scanner(System.in);
    String q = "";

    while (!q.equalsIgnoreCase("exit")) {
      jCas.reset();
      q = scanner.nextLine();


      jCas.setDocumentText(q);
      commodityEngine.process(jCas);

      Iterator<DictTerm> iterator = JCasUtil.iterator(jCas, DictTerm.class);

      if (!iterator.hasNext()) {
        System.out.println("Could not find any commodities in the text");
      } else {
        System.out.print("Commodities found: ");
        while (iterator.hasNext()) {
          DictTerm next = iterator.next();
          System.out.print(next.getDictCanon() + " ");
        }
        System.out.println();
      }


    }


  }
}
