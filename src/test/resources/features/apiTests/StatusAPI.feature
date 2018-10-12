Feature: Post a tweet to Twitter

  @status
  Scenario: User calls web service to post a tweet to Twitter
   # Given a user exists with OATH
    When a user post the tweet
      """
       This is my tweet from Rest Assured 
      """
    And User delete the tweet
