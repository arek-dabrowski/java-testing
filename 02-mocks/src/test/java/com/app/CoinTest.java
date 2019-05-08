package com.app;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoinTest {
    private Coin redCoin;
    private Coin greenCoin;

    @BeforeEach
    void setUp(){
        redCoin = new Coin(Color.RED);
        greenCoin = new Coin(Color.GREEN);
    }

    @Test
    void trivialTest(){
        assertThat(true).isTrue();
    }

    @Test
    void redCoin_Should_CreateSuccessfully(){
        assertThat(redCoin).isNotNull();
    }

    @Test
    void greenCoin_Should_CreateSuccessfully(){
        assertThat(greenCoin).isNotNull();
    }

    @Test
    void getColor_ShouldBeEqual_IfColorIsRed(){
        Color redColor = redCoin.getColor();

        assertThat(redColor).isEqualTo(Color.RED);
    }

    @Test
    void getColor_ShouldBeEqual_IfColorIsGreen(){
        Color greenColor = greenCoin.getColor();

        assertThat(greenColor).isEqualTo(Color.GREEN);
    }

    @Test
    void toString_ShouldBeEqual_IfFirstLetterIsR(){
        String redColor = redCoin.toString();

        assertThat(redColor).isEqualToIgnoringCase("R");
    }

    @Test
    void toString_ShouldBeEqual_IfFirstLetterIsG(){
        String greenColor = greenCoin.toString();

        assertThat(greenColor).isEqualToIgnoringCase("G");
    }


    @AfterEach
    void tearDown(){
        redCoin = null;
        greenCoin = null;
    }
}
