package com.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player player1;
    Player player2;
    Color redCoin = Color.RED;
    Color greenCoin = Color.GREEN;

    @BeforeEach
    void setUp(){
        player1 = new Player("Janoosh", redCoin);
        player2 = new Player("John", greenCoin);
    }

    @Test
    void trivialTest(){
        assertThat(true, is(true));
    }

    @Test
    void player_Should_CreateSuccessfully(){
        assertThat(player1, is(notNullValue()));
    }

    @Test
    void toString_ShouldBeEqual_ToPlayersName(){
        String name = "Adam";
        Player testPlayer = new Player(name, greenCoin);

        assertThat(testPlayer.toString(), is(equalToIgnoringCase(name)));
    }

    @Test
    void getCoinColor_ShouldBeEqual_ToRed(){
        assertThat(player1.getCoinColor(), is(redCoin));
    }

    @Test
    void getCoinColor_ShouldBeEqual_ToGreen(){
        assertThat(player2.getCoinColor(), is(greenCoin));
    }

    @Test
    void getWins_ShouldBeEqual_To0(){
        assertThat(player1.getWins(), is(0));
    }

    @Test
    void getLoses_ShouldBeEqual_To0(){
        assertThat(player1.getLoses(), is(0));
    }



    @AfterEach
    void tearDown(){
        player1 = null;
    }
}
