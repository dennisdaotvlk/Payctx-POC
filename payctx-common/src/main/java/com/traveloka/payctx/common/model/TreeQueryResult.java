package com.traveloka.payctx.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * @author sandeepandey
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeQueryResult {

  private List<Collection<String>> eligibleCombination;


}
