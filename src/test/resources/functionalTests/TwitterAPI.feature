Feature: Post a tweet to Twitter

  @Twitter
  Scenario: User calls web service to post a tweet to Twitter
    Given a user exists with OATH
    When a user post the tweet
      """
       This is my tweet from Rest Assured 
      """
    And user read the tweet
    And User delete the tweet
