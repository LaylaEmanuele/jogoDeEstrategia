package classes;

import java.util.ArrayList;

public class Utils {
  public static Aldeao getAldeaoById(ArrayList<Aldeao> aldeoes, int id) {
    for (Aldeao aldeao : aldeoes) {
      if (aldeao.pegaId() == id) {
        return aldeao;
      }
    }

    return null;
  }
}
