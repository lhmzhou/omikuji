package com.omikuji.apis;

import graphql.schema.DataFetcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MockGraphQLDataFetcher {

  private static List<Map<String, String>> songs =
      Arrays.asList(
          Map.of(
              "id",
              "song-1",
              "name",
              "When Doves Cry",
              "wordCount",
              "327",
              "writerId",
              "songwriter-1"),
          Map.of(
              "id", 
              "song-2", 
              "name", 
              "The Times They Are A-Changin", 
              "wordCount", 
              "246", 
              "writerId", 
              "songwriter-2"),
          Map.of(
              "id", 
              "song-2", 
              "name", 
              "Big Yellow Taxi", 
              "wordCount", 
              "270", 
              "writerId", 
              "songwriter-2"));

  private static List<Map<String, String>> songwriters =
      Arrays.asList(
          Map.of("id", "songwriter-1", "firstName", "Prince", "lastName", "Nelson"),
          Map.of("id", "songwriter-2", "firstName", "Bob", "lastName", "Dylan"),
          Map.of("id", "songwriter-2", "firstName", "Joni", "lastName", "Mitchell"));

  public static DataFetcher getSongByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String songId = dataFetchingEnvironment.getArgument("id");
      return songs.stream().filter(song -> song.get("id").equals(songId)).findFirst().orElse(null);
    };
  }

  public static DataFetcher getSongwriterDataFetcher() {
    return dataFetchingEnvironment -> {
      Map<String, String> song = dataFetchingEnvironment.getSource();
      String writerId = song.get("writerId");
      return songwriters.stream()
          .filter(songwriter -> songwriter.get("id").equals(writerId))
          .findFirst()
          .orElse(null);
    };
  }
}
