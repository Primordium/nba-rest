package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NbaApiParserTest {


    @Test
    public void getPlayerScores() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PlayerScoresDto> playerScoresDtoList;
        PlayerScoresDto firstPlayer;
        PlayerScoresDto secondPlayer;
        playerScoresDtoList = objectMapper.readValue(getDataToTestSearchById(),
                new TypeReference<List<PlayerScoresDto>>() {
                });

        firstPlayer = playerScoresDtoList.get(0);
        secondPlayer = playerScoresDtoList.get(1);

        assertEquals(firstPlayer.getFirstName(), "Allen");
        assertEquals(firstPlayer.getLastName(), "Leavell");
        assertEquals(firstPlayer.getScore(), 17);

        assertEquals(secondPlayer.getFirstName(), "Buck");
        assertEquals(secondPlayer.getLastName(), "Johnson");
        assertEquals(secondPlayer.getScore(), 8);

        assertEquals(playerScoresDtoList.size(), 2);
    }


    @Test
    public void getAllStatsByDate() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<GameDto> gameDtoList;
        GameDto firstGameDto;
        GameDto secondGameDto;

        gameDtoList = objectMapper.readValue(getDataToTestSeachByDate(), new TypeReference<List<GameDto>>() {});
        firstGameDto = gameDtoList.get(0);
        secondGameDto = gameDtoList.get(1);

        assertEquals(firstGameDto.getGameId(), 40368);
        assertEquals(firstGameDto.getHomeTeamName(), "Brooklyn Nets");
        assertEquals(firstGameDto.getHomeTeamScore(), 94);
        assertEquals(firstGameDto.getVisitorTeamName(), "New York Knicks");
        assertEquals(firstGameDto.getVisitorTeamScore(), 85);

        assertEquals(secondGameDto.getGameId(), 41430);
        assertEquals(secondGameDto.getHomeTeamName(), "Atlanta Hawks");
        assertEquals(secondGameDto.getHomeTeamScore(), 101);
        assertEquals(secondGameDto.getVisitorTeamName(), "Milwaukee Bucks");
        assertEquals(secondGameDto.getVisitorTeamScore(), 104);

        assertEquals(gameDtoList.size(), 2);
    }



    public String getDataToTestSearchById() {
        return "[\n" +
                "  {\n" +
                "    \"game\": {\n" +
                "      \"id\": 42122,\n" +
                "      \"date\": \"1988-03-05T00:00:00.000Z\",\n" +
                "      \"home_team_id\": 11,\n" +
                "      \"home_team_score\": 105,\n" +
                "      \"period\": 4,\n" +
                "      \"postseason\": false,\n" +
                "      \"season\": 1987,\n" +
                "      \"status\": \"Final\",\n" +
                "      \"time\": \" \",\n" +
                "      \"visitor_team_id\": 26,\n" +
                "      \"visitor_team_score\": 94\n" +
                "    },\n" +
                "    \"player\": {\n" +
                "      \"id\": 2456,\n" +
                "      \"first_name\": \"Allen\",\n" +
                "      \"height_feet\": null,\n" +
                "      \"height_inches\": null,\n" +
                "      \"last_name\": \"Leavell\",\n" +
                "      \"position\": \"\",\n" +
                "      \"team_id\": 11,\n" +
                "      \"weight_pounds\": null\n" +
                "    },\n" +
                "    \"pts\": 17,\n" +
                "    \"team\": {\n" +
                "      \"id\": 11,\n" +
                "      \"abbreviation\": \"HOU\",\n" +
                "      \"city\": \"Houston\",\n" +
                "      \"conference\": \"West\",\n" +
                "      \"division\": \"Southwest\",\n" +
                "      \"full_name\": \"Houston Rockets\",\n" +
                "      \"name\": \"Rockets\"\n" +
                "    },\n" +
                "    \"turnover\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"game\": {\n" +
                "      \"id\": 42122,\n" +
                "      \"date\": \"1988-03-05T00:00:00.000Z\",\n" +
                "      \"home_team_id\": 11,\n" +
                "      \"home_team_score\": 105,\n" +
                "      \"period\": 4,\n" +
                "      \"postseason\": false,\n" +
                "      \"season\": 1987,\n" +
                "      \"status\": \"Final\",\n" +
                "      \"time\": \" \",\n" +
                "      \"visitor_team_id\": 26,\n" +
                "      \"visitor_team_score\": 94\n" +
                "    },\n" +
                "    \"player\": {\n" +
                "      \"id\": 3036,\n" +
                "      \"first_name\": \"Buck\",\n" +
                "      \"height_feet\": null,\n" +
                "      \"height_inches\": null,\n" +
                "      \"last_name\": \"Johnson\",\n" +
                "      \"position\": \"\",\n" +
                "      \"team_id\": 11,\n" +
                "      \"weight_pounds\": null\n" +
                "    },\n" +
                "    \"pts\": 8,\n" +
                "    \"team\": {\n" +
                "      \"id\": 11,\n" +
                "      \"abbreviation\": \"HOU\",\n" +
                "      \"city\": \"Houston\",\n" +
                "      \"conference\": \"West\",\n" +
                "      \"division\": \"Southwest\",\n" +
                "      \"full_name\": \"Houston Rockets\",\n" +
                "      \"name\": \"Rockets\"\n" +
                "    },\n" +
                "    \"turnover\": 2\n" +
                "  }\n" +
                "]";
    }

    public String getDataToTestSeachByDate() {
        return "[\n" +
                "  {\n" +
                "    \"id\": 40368,\n" +
                "    \"date\": \"1988-03-05T00:00:00.000Z\",\n" +
                "    \"home_team\": {\n" +
                "      \"id\": 3,\n" +
                "      \"abbreviation\": \"BKN\",\n" +
                "      \"city\": \"Brooklyn\",\n" +
                "      \"conference\": \"East\",\n" +
                "      \"division\": \"Atlantic\",\n" +
                "      \"full_name\": \"Brooklyn Nets\",\n" +
                "      \"name\": \"Nets\"\n" +
                "    },\n" +
                "    \"home_team_score\": 94,\n" +
                "    \"period\": 4,\n" +
                "    \"postseason\": false,\n" +
                "    \"season\": 1987,\n" +
                "    \"status\": \"Final\",\n" +
                "    \"time\": \" \",\n" +
                "    \"visitor_team\": {\n" +
                "      \"id\": 20,\n" +
                "      \"abbreviation\": \"NYK\",\n" +
                "      \"city\": \"New York\",\n" +
                "      \"conference\": \"East\",\n" +
                "      \"division\": \"Atlantic\",\n" +
                "      \"full_name\": \"New York Knicks\",\n" +
                "      \"name\": \"Knicks\"\n" +
                "    },\n" +
                "    \"visitor_team_score\": 85\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 41430,\n" +
                "    \"date\": \"1988-03-05T00:00:00.000Z\",\n" +
                "    \"home_team\": {\n" +
                "      \"id\": 1,\n" +
                "      \"abbreviation\": \"ATL\",\n" +
                "      \"city\": \"Atlanta\",\n" +
                "      \"conference\": \"East\",\n" +
                "      \"division\": \"Southeast\",\n" +
                "      \"full_name\": \"Atlanta Hawks\",\n" +
                "      \"name\": \"Hawks\"\n" +
                "    },\n" +
                "    \"home_team_score\": 101,\n" +
                "    \"period\": 4,\n" +
                "    \"postseason\": false,\n" +
                "    \"season\": 1987,\n" +
                "    \"status\": \"Final\",\n" +
                "    \"time\": \" \",\n" +
                "    \"visitor_team\": {\n" +
                "      \"id\": 17,\n" +
                "      \"abbreviation\": \"MIL\",\n" +
                "      \"city\": \"Milwaukee\",\n" +
                "      \"conference\": \"East\",\n" +
                "      \"division\": \"Central\",\n" +
                "      \"full_name\": \"Milwaukee Bucks\",\n" +
                "      \"name\": \"Bucks\"\n" +
                "    },\n" +
                "    \"visitor_team_score\": 104\n" +
                "  }\n" +
                "]";
    }
}