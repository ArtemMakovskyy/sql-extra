-- 1. Category Revenue Percentage by Continent
SELECT sp.continent,
    SUM(p.price) AS revenue,
    SUM(CASE WHEN p.category = 'Bookcases & shelving units' THEN p.price ELSE 0 END) AS revenue_from_bookcases,
    ROUND(
        SUM(CASE WHEN p.category = 'Bookcases & shelving units' THEN p.price ELSE 0 END) * 100.0 /
        NULLIF(SUM(p.price), 0),
    2) AS revenue_from_bookcases_percent
FROM orders o
JOIN products p ON o.item_id = p.item_id
JOIN session_params sp ON o.ga_session_id = sp.ga_session_id
GROUP BY sp.continent
ORDER BY revenue DESC;

-- 2. Sessions by Browser with Empty Language
SELECT browser,
    COUNT(ga_session_id) AS session_cnt,
    COUNT(CASE WHEN language IS NULL OR language = '' THEN 1 END)
        AS session_cnt_with_empty_language,
    ROUND(
        COUNT(CASE WHEN language IS NULL OR language = '' THEN 1 END) * 100.0 /
        NULLIF(COUNT(ga_session_id), 0),
    2) AS session_cnt_with_empty_language_percent
FROM session_params
GROUP BY browser
ORDER BY session_cnt DESC;

-- 3. Product Quantity by Category Group
SELECT
  CASE
    WHEN position('furniture' in category) > 0 THEN 'furniture'
    WHEN position('units' in category) > 0 THEN 'units'
    ELSE 'other'
  END AS category_group,
  COUNT(*) AS quantity
FROM products
GROUP BY category_group;

-- 4. Extract Size from Product Description
SELECT
  short_description,
  CASE
    WHEN position('x' in short_description) > 0 AND position('cm' in short_description) > 0
    THEN SUBSTRING(short_description FROM position('x' in short_description) + 1 FOR length(short_description))
    ELSE NULL
  END AS size
FROM products;
