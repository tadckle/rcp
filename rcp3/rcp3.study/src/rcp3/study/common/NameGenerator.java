package rcp3.study.common;

import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

/**
 * It is used to generate a contiguous increment name. It finds the max number of existing
 * name pattern and increase the number by one to create a new name. For example name
 * pattern is "NewName" plus number and existing names are "NewName1, NewName4, NewName5",
 * then next new name is "NewName6".
 *
 * <p><b>Usage:</b>
 *
 * <pre>{@code
 * List<String> existingNames = Lists.newArrayList("Trump", "NewName1",
 *      "NewName4", "KFC" ,"NewName5", "Custom Name");
 * NameGenerator nameGenerator = new NameGenerator("NewName");
 * // nextName is "NewName6".
 * String nextName = nameGenerator.next(existingNames);
 * }</pre>
 *
 * @author alzhang
 */
public class NameGenerator {

  private long startFrom = 1;
  private String namePattern = "New";

  /**
   * Create an NameGenerator.
   *
   * @param namePattern the name pattern.
   */
  public NameGenerator(String namePattern) {
    if (!Strings.isNullOrEmpty(namePattern)) {
      this.namePattern = namePattern;
    }
  }

  /**
   * Find the next name according to given existing names. Not matched name will be simply filtered out.
   *
   * @param existingNames a list of existing names.
   * @return a string of next name.
   */
  public String next(List<String> existingNames) {
    if (existingNames == null) {
      return createName(startFrom);
    }

    Pattern pattern = Pattern.compile(String.format("(%s{1})\\s*(\\d*)", namePattern));
    OptionalLong maxNumOpt = existingNames.stream()
        .filter(Objects::nonNull)
        .map(str -> {
          Matcher matcher = pattern.matcher(str);
          return matcher.find() ? Longs.tryParse(matcher.group(2)) : null;
        }).filter(Objects::nonNull)
        .mapToLong(Long::longValue)
        .max();

    long nextNum = maxNumOpt.isPresent() ? maxNumOpt.getAsLong() + 1 : startFrom;
    return createName(nextNum);
  }

  private String createName(long index) {
    return String.format("%s%d", namePattern, index);
  }

}
