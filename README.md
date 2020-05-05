# Auction Coding Challenge

Here is my answer to an auction coding challenge that was presented to me. This package is a complete
package that can be built and executed within a Docker container.

## Running the auctions

On start-up, your program should load the config file (`config.json`) which lists
all valid sites and bidders, and their respective configurations.

The program should then load the input (JSON) from standard input that contains
a list of auctions to run. Each auction lists the site, which ad units are being
bid on, and a list of bids that have been requested on your behalf.

For each auction, you should find the highest valid bidder for each ad unit, after
applying the adjustment factor. An adjustment factor of -0.01 means that bids are
reduced by 1%; an adjustment of 0.05 would increase them by 5%. (Positive
adjustments are rare in real life, but possible.)
For example, a bid of $0.95 and an adjustment
factor of 0.05 (adjusted to $0.9975) will beat a bid of $1.10 with an adjustment
factor of -0.1 (adjusted to $0.99). When reporting the winners, use the bid
amounts provided by the bidder, rather than the adjusted values.

It is possible that a bidder will submit multiple bids for the same ad unit in
the same auction.

A bid is invalid and should be ignored if the bidder is not permitted to bid on
this site, the bid is for an ad unit not involved in this auction, the bidder
is unknown, or if the *adjusted* bid value is less than the site's floor.

An auction is invalid if the site is unrecognized, or there are no valid bids.
In the case of an invalid auction, just return an empty list.

The output of your program should be a list of auction results. The result of
each auction is a list of winning bids.

## Testing

Unit testsm with mocks have also been provided in order to ensure that the above rules are upheld.

## Docker build and execution

```bash
$ docker build -t challenge .
$ docker run -i -v /path/to/challenge/config.json:/auction/config.json challenge < /path/to/challenge/input.json
```

